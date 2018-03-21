import com.restfb.*;
import com.restfb.types.GraphResponse;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.MediaAttachment;
import com.restfb.types.send.Message;

import java.util.Iterator;
import java.util.List;

/**
 * Created by tri.tran on 3/21/2018.
 */
public class Main {
    public static void main(String[] args) {
        // the access token can be found in your Facebook app in the messenger section
        String pageAccessToken = "...";

        // create a version 2.6 client
        FacebookClient facebookClient = new DefaultFacebookClient(pageAccessToken, Version.VERSION_2_6);

        getBasicInfo(facebookClient); // OK

//        getFeedOfPage(facebookClient);  // OK

//        sendPrivateMsg(facebookClient);   // OK

    }

    private static void sendPrivateMsg(FacebookClient facebookClient) {
        IdMessageRecipient recipient = new IdMessageRecipient("10202989806958658");

        Message simpleTextMessage = new Message("Just a simple text");
        MediaAttachment image =
                new MediaAttachment(MediaAttachment.Type.IMAGE, "http://example.com/testimage.jpg");
        Message imageMessage = new Message(image);

        facebookClient.publish("t_1008785457/messages", GraphResponse.class,
                Parameter.with("recipient", recipient),
                Parameter.with("message", simpleTextMessage));
    }

    /*
        421748394605437_1601695463277385: pageId_postId
        Will load pageId and all postId of that page.
     */
    private static void getFeedOfPage(FacebookClient facebookClient) {
        Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);

        // Get the iterator
        Iterator<List<Post>> it = myFeed.iterator();

        while(it.hasNext()) {
            List<Post> myFeedPage = it.next();

            // This is the same functionality as the example above
            for (Post post : myFeedPage) {
                System.out.println("Post: " + post);
                System.out.println("Page Id _ Post Id: " + post.getId());
            }
        }
    }

    private static void getBasicInfo(FacebookClient facebookClient) {
        User user = facebookClient.fetchObject("me", User.class);
        Page page = facebookClient.fetchObject("cocacola", Page.class,
                Parameter.with("fields", "fan_count"));

        System.out.println("User name: " + user.getName());
        System.out.println("Page likes: " + page.getFanCount());
    }
}
