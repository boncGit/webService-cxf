package gdsbcw_cxf;

import gdsbcw_cxf.entity.A;
import gdsbcw_cxf.entity.User;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mengxy on 2018/5/25.
 */

@WebService(name="BankTransaction")//@WebService：将该接口发布成WebService服务器
public interface BankTransaction {
    public String getAesSeed( @WebParam( name="sid") String sid);
    public String synAesSeed( @WebParam( name="sid") String sid, @WebParam(name="seed") String seed);
    public String requestBank(@WebParam( name="sid") String sid, @WebParam(name="xml") String xml);
    public String getUserList(@WebParam( name="sid") String sid,
                                  @WebParam(name = "userList") List<User> userList,
                                  @WebParam(name = "alist") List<A> alist);
}
