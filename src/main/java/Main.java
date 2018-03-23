import com.restfb.*;
import com.restfb.types.GraphResponse;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.util.Iterator;
import java.util.List;

/**
 * Created by tri.tran on 3/21/2018.
 */
public class Main {

    // the access token can be found in your Facebook app in the messenger section
    final static String PAGE_ACCESS_TOKEN = "...";
    final static String MY_APP_ID = "...";
    final static String MY_APP_SECRET = "...";


    public static void main(String[] args) {

        // create a version 2.6 client
        FacebookClient facebookClient = new DefaultFacebookClient(PAGE_ACCESS_TOKEN, Version.VERSION_2_6);

//        getBasicInfo(facebookClient); // OK

//        getFeedOfPage(facebookClient);  // OK

//        addNewMsgToConversation(facebookClient);  // OK

//        getExtendAccessToken(); // OK

    }


    private static void getExtendAccessToken() {
        FacebookClient.AccessToken accessToken = new DefaultFacebookClient().obtainExtendedAccessToken(MY_APP_ID, MY_APP_SECRET, PAGE_ACCESS_TOKEN);
        System.out.println("My extended access token: " + accessToken);
    }

    private static void addNewMsgToConversation(FacebookClient facebookClient) {
        Connection<Post> myFeed = facebookClient.fetchConnection("me/conversations", Post.class);
        List<Post> list = myFeed.getData();
        for (Post post : list) {
            System.out.println("Get Post Id: " + post.getId());
        }

        String simpleTextMessage = new String("Just a simple text");
        facebookClient.publish("t_1008785457/messages", GraphResponse.class, Parameter.with("message", simpleTextMessage));
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
