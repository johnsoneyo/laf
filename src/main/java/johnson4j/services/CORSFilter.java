/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.services;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 *
 * @author Guest
 */
public class CORSFilter  implements ContainerResponseFilter {

    @Override
    public ContainerResponse filter(ContainerRequest creq, ContainerResponse cresp) {
       
        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Origin", "*");
        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With,Authorization,Origin");

        return cresp;
    }
    
    
}
