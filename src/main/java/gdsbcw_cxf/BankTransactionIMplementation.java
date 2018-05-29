package gdsbcw_cxf;

import gdsbcw_cxf.entity.A;
import gdsbcw_cxf.entity.User;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mengxy on 2018/5/25.
 */

@WebService(targetNamespace="http://gdsbcw_cxfxzh/")
public class BankTransactionIMplementation implements BankTransaction {
    @Override
    public String getAesSeed(String sid) {
        return "<businessCode>123</businessCode>\n" +
                "<userName>test</userName>\n" +
                "<userCode>test</userCode>\n";
    }

    @Override
    public String synAesSeed(String sid, String seed) {
        return sid+seed;
    }

    @Override
    public String requestBank(String sid, String xml) {
        return sid+xml;
    }

    @Override
    public String getUserList(String sid, List<User> userList, List<A> alist) {
        return sid;
    }


}
