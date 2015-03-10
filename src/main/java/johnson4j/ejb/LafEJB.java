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
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import johnson4j.dto.PasswordToken;
import johnson4j.dto.TokenStatus;
import johnson4j.dto.UpdateUser;
import johnson4j.dto.User;
import johnson4j.entity.Events;
import johnson4j.entity.LafUser;
import johnson4j.entity.LafUserMedia;
import johnson4j.entity.LafUserToken;
import johnson4j.exception.LafException;
import johnson4j.util.LafBundle;
import johnson4j.util.TokenGenerator;
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
    @EJB
    TokenGenerator tokn;
    @Resource
    TimerService time;

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
        u.setPhone(usr.getPhone());
        u.setScreenName(usr.getScreen_name());
        u.setDateCreated(new Date());
        u.setDateModified(new Date());
        u.setCredit(100.00);
        em.persist(u);
        LafUserMedia usm = new LafUserMedia();
        usm.setLafId(u.getLafId());
        em.merge(usm);

//        Calendar c = Calendar.getInstance();
//        c.setTime(u.getDob());
//        ScheduleExpression birthDay = new ScheduleExpression().
//                dayOfMonth(Calendar.DAY_OF_MONTH).month(Calendar.MONTH);
//        
//        timerService.createCalendarTimer(birthDay, new TimerConfig(u, true));

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
        } catch (NullPointerException np) {
            throw new LafException("please provide a date");
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

    public List<Events> getEvents(String count) {

        Query q = em.createNativeQuery("select * from events order by created_time desc limit " + Integer.parseInt(count), Events.class);
        List<Events> resultList = q.getResultList();
        return resultList;
    }

    public LafUser getUser(String user_id) throws LafException {
        LafUser u = em.find(LafUser.class, Integer.parseInt(user_id));

        if (u != null) {
            return u;
        } else {
            throw new LafException("user not found");
        }

    }

    public LafUserToken resetPassword(String email) throws LafException {
        Query q = em.createQuery("select l from LafUser l where l.email =:email");
        q.setParameter("email", email);

        try {
            LafUser lu = (LafUser) q.getSingleResult();

            String token = tokn.generatePasswordToken();
            LafUserToken lut = new LafUserToken();

            PasswordToken pt = this.createPasswordToken(lu.getLafId());

            Date d = pt.getExpiry_time();
            Calendar cl = Calendar.getInstance();
            cl.setTime(d);

            ScheduleExpression se = new ScheduleExpression().second(cl.get(Calendar.SECOND))
                    .minute(cl.get(Calendar.MINUTE)).hour(cl.get(Calendar.HOUR));
            time.createCalendarTimer(se, new TimerConfig(pt, true));

            lut.setTokenValue(token);
            lut.setStatus(TokenStatus.ACTIVE);
            lut.setExpiryDate(pt.getExpiry_time());
            lut.setCreationDate(pt.getCreated_time());
            lut.setLafId(lu.getLafId());
 
            em.merge(lut);

            sendEmail(lu.getEmail(),lut.getTokenValue());
            return lut;

            
        } catch (NoResultException no) {
            throw new LafException("email not associated with any account");
        }



    }

    private PasswordToken createPasswordToken(int laf_id) {
        Calendar present = Calendar.getInstance();
        Calendar future = Calendar.getInstance();
        int hour = future.get(Calendar.HOUR_OF_DAY);
        int min = future.get(Calendar.MINUTE);
        future.add(Calendar.MINUTE, 30);

        return new PasswordToken(laf_id, present.getTime(), future.getTime());
    }

    
    @Timeout
    public void changeTokenStatus(Timer timer) {

        PasswordToken p = (PasswordToken) timer.getInfo();
        LafUserToken lu = em.find(LafUserToken.class, p.getLaf_id());
        lu.setStatus(TokenStatus.EXPIRED);
        em.merge(lu);
       
        
    }

    private void sendEmail(String email,String token) {
     final String username = "johnsoneyo@gmail.com";
        final String password = "freaks03";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setSentDate(new java.util.Date());
            message.setFrom(new InternetAddress("johnsoneyo@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("LAF Password Reset ");
            message.setText("LAFToken password Token : "+token+"Please disregard mail if you didnt request "
            + "this change, token expires in 30 mins");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
   
    
    }

    public void changePassword(String token, String password) throws LafException{
        Query q = em.createQuery("select l from LafUserToken l where l.tokenValue = :tokenValue");
        q.setParameter("tokenValue", token);
        
        
        try{
          LafUserToken lf = (LafUserToken)q.getSingleResult();
        LafUser lu =  em.find(LafUser.class, lf.getLafId());
           if(lf.getStatus().equals(TokenStatus.EXPIRED)){
               throw new LafException("Token expired ");
           }else{
                lu.setPassword(new String(Base64.encodeBase64(password.getBytes())));
                em.merge(lu);
           }
               
        }catch(NoResultException no){
            
        }
    }
    
    
    
}

