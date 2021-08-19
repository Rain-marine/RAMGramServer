package controllers;

import models.*;
import models.types.MessageLink;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageController implements Repositories {
    private final static Logger log = LogManager.getLogger(MessageController.class);

    public MessageController() {
    }

    public ArrayList<Long> getSavedMessage(long userId) {
        User user = USER_REPOSITORY.getById(userId);
        List<Message> messages = user.getFavoriteMessages();
        ArrayList<Long> messageIDs = new ArrayList<>();
        for (Message message : messages) {
            if (!message.isDeleted())
                messageIDs.add(message.getId());
        }
        return messageIDs;
    }

    public ArrayList<Long> getSavedTweets(long userId) {
        User user = USER_REPOSITORY.getById(userId);
        List<Tweet> tweets = user.getFavoriteTweets();
        ArrayList<Long> tweetIDs = new ArrayList<>();
        for (Tweet tweet : tweets) {
            tweetIDs.add(tweet.getId());
        }
        return tweetIDs;
    }

    public boolean canSendMessageToUser(String userToSendMessage, long loggedUserId) {
        User user = USER_REPOSITORY.getByUsername(userToSendMessage);
        if (user == null || !user.isActive())
            return false;
        if (hasFollow(user.getFollowers(), loggedUserId))
            return true;
        return isFollower(user.getFollowings(), loggedUserId);
    }

    private boolean isFollower(List<User> followings, long loggedUserId) {
        for (User user : followings) {
            if (user.getId() == loggedUserId)
                return true;
        }
        return false;
    }

    private boolean hasFollow(List<User> followers, long loggedUserId) {
        for (User user : followers) {
            if (user.getId() == loggedUserId)
                return true;
        }
        return false;
    }

    public boolean canSendMessageToGroup(String groupToSendMessage, long loggedUserId) {
        User user = USER_REPOSITORY.getById(loggedUserId);
        for (Group group : user.getGroups()) {
            if (group.getName().equals(groupToSendMessage))
                return true;
        }
        return false;
    }

    public void sendMessage(String message, byte[] image, List<String> users, List<String> groupsToSendMessage, long loggedUserId, MessageLink link) {
        User user = USER_REPOSITORY.getById(loggedUserId);
        List<Chat> userChats = user.getChats();
        List<Group> groups = user.getGroups();
        HashMap<String, Group> groupNameToGroup = extractGroupNameToGroup(groups);

        sendMessageToUsers(message, image, users, userChats, loggedUserId, link);
        if (groupsToSendMessage != null)
            sendMessageToGroups(message, image, groupsToSendMessage, groupNameToGroup, userChats, loggedUserId);
    }


    private void sendMessageToGroups(String message, byte[] image, List<String> groupsToSendMessage, HashMap<String, Group> groups, List<Chat> chats, long loggedUserId) {
        for (String groupName : groupsToSendMessage) {
            List<String> users = new ArrayList<>();
            Group faction = FACTION_REPOSITORY.getFactionById(groups.get(groupName).getId());
            faction.getMembers().forEach(member -> users.add(member.getUsername()));
            sendMessageToUsers(message, image, users, chats, loggedUserId, MessageLink.NONE);
        }
    }

    private void sendMessageToUsers(String message, byte[] image, List<String> users, List<Chat> chats, long loggedUserId, MessageLink link) {
        for (String user : users) {
            boolean hasSent = false;
            User loggedUser = USER_REPOSITORY.getById(loggedUserId);
            User receiver = USER_REPOSITORY.getByUsername(user);
            Message newMessage = new Message(message, image, loggedUser, receiver);
            newMessage.setLink(link);
            for (Chat chat : chats) {
                if (chat.getUserChats().size() == 2 &&
                        (chat.getUserChats().get(0).getUser().getUsername().equals(user)
                                || chat.getUserChats().get(1).getUser().getUsername().equals(user))) {
                    CHAT_REPOSITORY.addMessageToChat(chat.getId(), newMessage);
                    hasSent = true;
                    break;
                }
            }
            if (!hasSent) {
                Chat newChat = new Chat(new ArrayList<>() {
                    {
                        add(loggedUser);
                        add(receiver);
                    }
                });
                CHAT_REPOSITORY.insert(newChat);
                Chat chat = getChatWithId(receiver.getId(), loggedUserId);
                CHAT_REPOSITORY.addMessageToChat(chat.getId(), newMessage);
            }
        }
    }

    public void insertSavedMessage(long messageId, long loggedUserId) {
        MESSAGE_REPOSITORY.addMessageToSavedMessage(loggedUserId, MESSAGE_REPOSITORY.getById(messageId));
    }

    private HashMap<String, Group> extractGroupNameToGroup(List<Group> groups) {
        HashMap<String, Group> groupNameToGroup = new HashMap<>();
        groups.forEach(group -> groupNameToGroup.put(group.getName(), group));

        return groupNameToGroup;
    }

    public void forwardTweet(long tweetId, String receiver, long loggedUserId) {
        Tweet tweet = TWEET_REPOSITORY.getById(tweetId);
        String message = "Tweet forwarded from " + tweet.getUser().getUsername() + "\n" + tweet.getText();
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);
        User receiveUser = USER_REPOSITORY.getByUsername(receiver);
        Message newMessage = new Message(message, tweet.getImage(), loggedUser, receiveUser);
        newMessage.setGrandSender(tweet.getUser());

        for (Chat chat : loggedUser.getChats()) {
            if (chat.getUserChats().size() == 2 &&
                    (chat.getUserChats().get(0).getUser().getUsername().equals(receiver)
                            || chat.getUserChats().get(1).getUser().getUsername().equals(receiver))) {
                CHAT_REPOSITORY.addMessageToChat(chat.getId(), newMessage);
                return;
            }
        }
        Chat newChat = new Chat(new ArrayList<>() {
            {
                add(loggedUser);
                add(receiveUser);
            }
        });
        newChat.setMessages(new ArrayList<>() {
            {
                add(newMessage);
            }
        });
        newChat.getUserChats().get(1).setHasSeen(false);
        newChat.getUserChats().get(1).setUnseenCount(1);
        CHAT_REPOSITORY.insert(newChat);
    }

    public Chat getChatWithId(long userId, long loggedUserId) {
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);
        User receiveUser = USER_REPOSITORY.getById(userId);
        String username = receiveUser.getUsername();
        for (Chat chat : loggedUser.getChats()) {
            if (chat.getUserChats().size() == 2 &&
                    (chat.getUserChats().get(0).getUser().getUsername().equals(username)
                            || chat.getUserChats().get(1).getUser().getUsername().equals(username)))
                return chat;
        }

        Chat newChat = new Chat(new ArrayList<>() {
            {
                add(loggedUser);
                add(receiveUser);
            }
        });
        newChat.setMessages(new ArrayList<>());
        CHAT_REPOSITORY.insert(newChat);
        return null;
    }

    public void deleteMessage(long id) {
        MESSAGE_REPOSITORY.delete(id);
        log.info("message " + id + " was deleted");
    }

    public void editMessage(long messageId, String newText) {
        MESSAGE_REPOSITORY.editMessageText(messageId, newText);
    }

    public byte[] getMessageImage(long messageId) {
        return MESSAGE_REPOSITORY.getById(messageId).getImage();
    }

    public String getMessageText(long messageId) {
        return MESSAGE_REPOSITORY.getById(messageId).getText();
    }

    public String getMessageDate(long messageId) {
        return MESSAGE_REPOSITORY.getById(messageId).getDate().toString();
    }

    public String getMessageSender(long messageId) {
        return MESSAGE_REPOSITORY.getById(messageId).getSender().getUsername();
    }

    public String getMessageGrandSender(long messageId) {
        return MESSAGE_REPOSITORY.getById(messageId).getGrandSender().getUsername();
    }

    public byte[] getSenderProfile(long messageId) {
        return MESSAGE_REPOSITORY.getById(messageId).getSender().getProfilePhoto();
    }

    public void forward(long messageID, List<String> users, List<String> factions, long loggedUserId) {
        User user = USER_REPOSITORY.getById(loggedUserId);
        List<Chat> userChats = user.getChats();
        List<Group> groups = user.getGroups();
        HashMap<String, Group> groupNameToGroup = extractGroupNameToGroup(groups);

        forwardMessageToUsers(messageID, users, userChats, loggedUserId);
        forwardMessageToFactions(messageID, factions, groupNameToGroup, userChats, loggedUserId);
    }

    private void forwardMessageToFactions(long messageID, List<String> factions, HashMap<String, Group> groupNameToGroup, List<Chat> chats, long loggedUserId) {
        for (String groupName : factions) {
            List<String> users = new ArrayList<>();
            Group faction = FACTION_REPOSITORY.getFactionById(groupNameToGroup.get(groupName).getId());
            faction.getMembers().forEach(member -> users.add(member.getUsername()));
            forwardMessageToUsers(messageID, users, chats, loggedUserId);
        }
    }

    private void forwardMessageToUsers(long messageID, List<String> users, List<Chat> chats, long loggedUserId) {
        Message message = MESSAGE_REPOSITORY.getById(messageID);
        for (String user : users) {
            boolean hasSent = false;
            User loggedUser = USER_REPOSITORY.getById(loggedUserId);
            User receiver = USER_REPOSITORY.getByUsername(user);
            Message newMessage = new Message(message.getText(), message.getImage(), loggedUser, receiver);
            newMessage.setGrandSender(message.getSender());
            for (Chat chat : chats) {
                chat = CHAT_REPOSITORY.getById(chat.getId());
                if (chat.getUserChats().size() == 2 &&
                        (chat.getUserChats().get(0).getUser().getUsername().equals(user)
                                || chat.getUserChats().get(1).getUser().getUsername().equals(user))) {
                    CHAT_REPOSITORY.addMessageToChat(chat.getId(), newMessage);
                    hasSent = true;
                    break;
                }
            }
            if (!hasSent) {
                Chat newChat = new Chat(new ArrayList<>() {
                    {
                        add(loggedUser);
                        add(receiver);
                    }
                });
                CHAT_REPOSITORY.insert(newChat);
                Chat chat = getChatWithId(receiver.getId(), loggedUserId);
                CHAT_REPOSITORY.addMessageToChat(chat.getId(), newMessage);
            }
        }
    }

    public void addSavedMessage(String messageText, byte[] chosenImageByteArray, long loggedUserId) {
        Message message = new Message(messageText, chosenImageByteArray, USER_REPOSITORY.getById(loggedUserId));
        MESSAGE_REPOSITORY.addMessageToSavedMessage(loggedUserId, message);
    }

    public long getChatWithUser(long userId, long loggedUserId) {
        Chat chat = getChatWithId(userId, loggedUserId);
        if (chat == null)
            chat = getChatWithId(userId, loggedUserId);
        return chat.getId();
    }


    public enum TYPE {EDIT, DELETE, BOTH, NONE}

    public TYPE getMessageType(long messageId, long loggedUserId) {
        Message message = MESSAGE_REPOSITORY.getById(messageId);
        boolean editable = (message.getSender().getId() == message.getGrandSender().getId()
                && message.getSender().getId() == loggedUserId);
        boolean removable = (message.getSender().getId() == loggedUserId);
        if (editable && removable) {
            return TYPE.BOTH;
        } else if (editable) {
            return TYPE.EDIT;
        } else if (removable) {
            return TYPE.DELETE;
        } else
            return TYPE.NONE;
    }
}