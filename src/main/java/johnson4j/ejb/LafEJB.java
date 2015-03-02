/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.ejb;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import johnson4j.dto.UpdateUser;
import johnson4j.dto.User;
import johnson4j.entity.LafUser;
import johnson4j.entity.LafUserMedia;
import johnson4j.exception.LafException;
import johnson4j.util.LafBundle;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author johnson3yo
 */
//to do 
//backend email validation, dob scheduler on create customer
//backend validation on screen_name
@Stateless
public class LafEJB {

    private final Logger log = Logger.getLogger(getClass().getName());
    @PersistenceContext
    EntityManager em;
    @Resource
    TimerService timerService;

    public String getFaceBookDetail(String access_token) throws LafException {
        if (access_token != null) {

            String fbk = LafBundle.facebookGraph();
            String response = processRequest(fbk + "/me?field=id,name,picture&access_token=" + access_token);

            JsonElement dataElem = new JsonParser().parse(response);
            String socId = dataElem.getAsJsonObject().get("id").getAsString();

            return response;
        } else {
            throw new LafException("Access token is required");
        }
    }

    private String processRequest(String reqURL) {

        try {
            URL url = new URL(reqURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.connect();
            StringWriter sw = new StringWriter();
            InputStream is = request.getInputStream();
            IOUtils.copy(is, sw);
            return sw.toString();

        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }

    }

    private String processPostRequest(String reqURL) {

        try {
            URL url = new URL(reqURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("POST");
            request.connect();

            StringWriter sw = new StringWriter();

            InputStream is = request.getInputStream();
            IOUtils.copy(is, sw);

            return sw.toString();

        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }

    }

    public byte[] getFacebookPhoto(String id, int height, int width) {

        String fbk = LafBundle.facebookGraph();
        return processImageRequest(fbk + "/" + id + "/picture?width=" + width + "&height=" + height);

    }

    private byte[] processImageRequest(String reqURL) {

        try {
            URL url = new URL(reqURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.connect();

            StringWriter sw = new StringWriter();

            InputStream is = request.getInputStream();
            return IOUtils.toByteArray(is);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }


    }

    public LafUser createUser(johnson4j.dto.User usr) throws LafException {

        LafUser u = new LafUser();

        validateUserEmail(usr.getEmail());
        validateScreen(usr.getScreen_name());

        u.setFirstName(usr.getFirst_name());
        u.setLastName(usr.getLast_name());
        u.setEmail(usr.getEmail());
        u.setPassword(new String(Base64.encodeBase64(usr.getPassword().getBytes())));
        u.setDob(parseDate(usr.getDob()));
        u.setPhone(usr.getPhone());
        u.setScreenName(usr.getScreen_name());
        u.setDateCreated(new Date());
        u.setDateModified(new Date());
        u.setCredit(100.00);
        em.persist(u);
        LafUserMedia usm = new LafUserMedia();
        usm.setLafId(u.getLafId());
        em.merge(usm);

        Calendar c = Calendar.getInstance();
        c.setTime(u.getDob());
        ScheduleExpression birthDay = new ScheduleExpression().
                dayOfMonth(Calendar.DAY_OF_MONTH).month(Calendar.MONTH);

        timerService.createCalendarTimer(birthDay, new TimerConfig(u, true));

        return u;
    }

    public LafUser addFaceBook(String id, String laf_id) {
        LafUser usr = em.find(johnson4j.entity.LafUser.class, Integer.parseInt(laf_id));
        //        Query q = em.createNativeQuery("update user_social_media set facebook_id = '"+id+"' where user_id = ' "+laf_id+" ' ");
        //        q.executeUpdate();

        LafUserMedia lm = usr.getLafUserMedia();
        lm.setFacebookId(id);
        em.merge(lm);

        return usr;
    }

    private Date parseDate(String dob) throws LafException {
        Date d = null;
        try {
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            d = date.parse(dob);
        } catch (ParseException e) {
            throw new LafException(e.getMessage() + " Use yyyy-MM-dd format instead");
        }
        return d;

    }

    @Timeout
    public void sendBirthdayEmail(Timer timer) {
        LafUser lu = (LafUser) timer.getInfo();

        //send birthday email 

    }

    public String publishPost(String id, String message, String access_token, String link, String place) {
        String fbg = LafBundle.facebookGraph();

        return this.processPostRequest(fbg + "/" + id + "/feed?message=" + message + "&link=" + link + "&access_token=" + access_token);

    }

    private void validateUserEmail(String email) throws LafException {
        Query q = em.createQuery("select u from LafUser u where u.email = :email");
        q.setParameter("email", email);
        try {
            LafUser l = (LafUser) q.getSingleResult();

            if (l != null) {
                throw new LafException("email address already exists");
            }
        } catch (NoResultException no) {
            log.log(Level.INFO, "no such email exists");
        }

    }

    private void validateScreen(String screen_name) throws LafException {

        Query q = em.createQuery("select u from LafUser u where u.screenName = :screenName");
        q.setParameter("screenName", screen_name);
        try {
            LafUser l = (LafUser) q.getSingleResult();

            if (l != null) {
                throw new LafException("screen name already exists");
            }
        } catch (NoResultException no) {
            log.log(Level.INFO, "no such screen name");
        }
    }

    public LafUser login(User usr) throws LafException {

        Query q = em.createQuery("select l from LafUser l where l.email = :email and l.password = :password ");
        byte[] pwd = Base64.encodeBase64(usr.getPassword().getBytes());
        try {
            q.setParameter("email", usr.getEmail());
            q.setParameter("password", new String(pwd));
            return (LafUser) q.getSingleResult();
        } catch (NoResultException no) {
            throw new LafException("No such user");
        } catch (NullPointerException no) {
            throw new LafException("One missing field");
        }

    }

    public String getLafVideos(String maxResults) {

        String ytbe = LafBundle.youTubeV3();
        String googl_key = LafBundle.getGoogleKey();
        String channel_id = LafBundle.getChannel();
        return this.processRequest(ytbe + "/search?key=" + googl_key + "&channelId=" + channel_id + "&part=snippet,id&order=date&maxResults=" + maxResults);
    }

    public LafUser updateUser(UpdateUser usr) throws LafException {


        LafUser u = em.find(LafUser.class, Integer.parseInt(usr.getId()));
        if (u != null) {
            u.setDateModified(new Date());
            u.setDob(parseDate(usr.getDob()));
            u.setEmail(usr.getEmail());
            u.setFirstName(usr.getFirst_name());
            u.setLastName(usr.getLast_name());
            u.setPhone(usr.getPhone());
            em.merge(u);
            return u;
        } else {
            throw new LafException("No user found ");
        }

    }

    public void removeUser(String id) throws LafException {

        LafUser u = em.find(LafUser.class, Integer.parseInt(id));
        if (u != null) {
            em.remove(u);
        } else {
            throw new LafException("No user found ");
        }

    }
}
