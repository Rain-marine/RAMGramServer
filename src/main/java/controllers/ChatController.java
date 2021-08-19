package controllers;

import models.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.Repositories;

import java.util.*;
import java.util.stream.Collectors;

public class ChatController implements Repositories {
    private final static Logger log = LogManager.getLogger(ChatController.class);

    public ChatController() {
    }

    public ArrayList<Long> getChatsIds(long userId) {
        List<Chat> chats = CHAT_REPOSITORY.getAllChats(userId);
        ArrayList<Long> chatIds = new ArrayList<>();
        for (Chat chat : chats) {
            chatIds.add(chat.getId());
        }
        return chatIds;
    }

    public void seeChat(long chatId , long loggedUserId) {
        log.info("the chat " + chatId + " was seen by :" + loggedUserId);
        CHAT_REPOSITORY.clearUnSeenCount(chatId, loggedUserId);
    }

    public void addMessageToChat(long chatId, String message, byte[] images , long loggedUserId) {
        long frontUserId = getFrontUserId(chatId , loggedUserId);
        Message newMessage = new Message(message, images,
                USER_REPOSITORY.getById(loggedUserId),
                USER_REPOSITORY.getById(frontUserId));
        CHAT_REPOSITORY.addMessageToChat(chatId, newMessage);
        log.info(loggedUserId + " sent message to " + chatId);
    }

    public long getFrontUserId(long chatId , long loggedUserId) {
        User frontUser = getFrontUser(chatId , loggedUserId);
        return frontUser.getId();
    }

    private User getFrontUser(long chatId , long loggedUserId) {
        Chat chat = CHAT_REPOSITORY.getById(chatId);
        return chat.getUserChats().get(0).getUser().getId() == loggedUserId
                ? chat.getUserChats().get(1).getUser()
                : chat.getUserChats().get(0).getUser();
    }

    public ArrayList<Long> getMessages(long chatID) {
        Chat freshChat = CHAT_REPOSITORY.getById(chatID);
        ArrayList<Long> messageIDs = new ArrayList<>();
        List<Message> messages = freshChat.getMessages().stream().sorted(Comparator.comparing(Message::getDate)).collect(Collectors.toList());
        for (Message message : messages) {
            if (!message.isDeleted())
                messageIDs.add(message.getId());
        }
        return messageIDs;
    }

    public void createGroupChat(List<String> members, String name, long loggedUserId) {
        members.add(USER_REPOSITORY.getById(loggedUserId).getUsername());
        List<User> groupMembers = members.stream().map(USER_REPOSITORY::getByUsername).collect(Collectors.toList());
        Chat chat = new Chat(groupMembers, name);
        CHAT_REPOSITORY.insert(chat);
        log.info(" group chat " + chat.getId() + " was created by " + loggedUserId);
    }

    public void addMemberToGroupChat(String member, long chatId) {
        Chat chat = CHAT_REPOSITORY.getById(chatId);
        if (chat.getUserChats().stream().noneMatch(it -> it.getUser().getUsername().equals(member)))
            CHAT_REPOSITORY.addMemberToGroupChat(chatId, new UserChat(USER_REPOSITORY.getByUsername(member), chat));
    }

    public void addNewMessageToGroupChat(String message, byte[] image, long chatId , long loggedUserId) {
        Message newMessage = new Message(message, image, USER_REPOSITORY.getById(loggedUserId));
        CHAT_REPOSITORY.addMessageToChat(chatId, newMessage);
    }

    public String getChatName(Long chatId , long loggedUserId) {
        Chat chat = CHAT_REPOSITORY.getById(chatId);
        if (chat.isGroup()) {
            return chat.getName();
        } else {
            return getFrontUser(chatId, loggedUserId).getUsername();
        }
    }

    public String getUnseenCount(Long chatId , long loggedUserId) {
        Chat chat = CHAT_REPOSITORY.getById(chatId);
        UserChat userToSee = chat.getUserChats().stream().filter(it -> it.getUser().getId() == loggedUserId).findAny().orElseThrow();
        return String.valueOf(userToSee.getUnseenCount());
    }

    public boolean isGroup(Long chatId) {
        Chat chat = CHAT_REPOSITORY.getById(chatId);
        return chat.isGroup();
    }

    public ArrayList<String> getMembersNames(long groupId) {
        Chat chat = CHAT_REPOSITORY.getById(groupId);
        List<UserChat> userChats = chat.getUserChats();
        ArrayList<String> names = new ArrayList<>();
        for (UserChat userChat : userChats) {
            names.add(userChat.getUser().getUsername());
        }
        return names;
    }

    public void leaveGroup(long groupId , long loggedUserId) {
        CHAT_REPOSITORY.leaveGroup(loggedUserId , groupId);
    }

    public void addScheduledMessage(String message, byte[] image, long chatId, long loggedUserId, long time) {
        Timer timer = new Timer("MessageTimer");
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("inserting scheduled Message...");
                Message newMessage = new Message(message, image, USER_REPOSITORY.getById(loggedUserId));
                CHAT_REPOSITORY.addMessageToChat(chatId, newMessage);
            }
        };
        time*=1000;
        timer.schedule(task, time);
    }
}