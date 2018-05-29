package utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Mengxy on 2018/5/28.
 */
public class ArtifactOutInterceptor extends AbstractPhaseInterceptor<Message> {
    private static final Logger log = Logger.getLogger(ArtifactOutInterceptor.class);

    public ArtifactOutInterceptor() {
        //这儿使用pre_stream，意思为在流关闭之前
        super(Phase.PRE_STREAM);
    }


    public void handleMessage(Message message) {

        try {

            OutputStream os = message.getContent(OutputStream.class);

            CachedStream cs = new CachedStream();

            message.setContent(OutputStream.class,cs);

            message.getInterceptorChain().doIntercept(message);

            CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class);
            InputStream in = csnew.getInputStream();

            String xml = IOUtils.toString(in);
            System.out.println("replaceBegin"+xml);

            xml=xml.replace("return", "receiveReturn")
                    .replace("xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"",
                            "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
                    .replace("xmlns:ns2=\"http://service.webservice.jeesite.thinkgem.com/\"","xmlns:ns2=\"http://service.webservice.jeesite.thinkgem.com/\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"")
                    //.replace("xmlns:ns2=\"http://localhost:8080/mycrm/webservice/customerService\"","xmlns:ns1=\"http://tzql.webservice.com/\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"")
                    .replace("<return>", "<receiveReturn>")
                    .replace("</return>", "</receiveReturn>")
                    .replace("soap:", "soapenv:")
                    .replace("<receiveReturn>", "<receiveReturn xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"soapenc:string\">")
                    //.replace("soapenvenv", "soapenv")

                    .replace("ns2", "ns1");

            String dealStr = xml.replace("&lt;",'<'+"").replace("&gt;",'>'+"");

            System.out.println("replaceAfter"+dealStr);
            //这里对xml做处理，处理完后同理，写回流中
            IOUtils.copy(new ByteArrayInputStream(dealStr.getBytes("UTF-8")), os);

            cs.close();
            os.flush();

            message.setContent(OutputStream.class, os);

        } catch (Exception e) {
            log.error("Error when split original inputStream. CausedBy : " + "\n" + e);
        }
    }

    private class CachedStream extends CachedOutputStream {

        public CachedStream() {

            super();

        }

        protected void doFlush() throws IOException {

            currentStream.flush();

        }

        protected void doClose() throws IOException {

        }

        protected void onWrite() throws IOException {

        }

    }
}
