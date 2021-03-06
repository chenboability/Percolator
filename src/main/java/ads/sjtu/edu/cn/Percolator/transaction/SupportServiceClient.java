package ads.sjtu.edu.cn.Percolator.transaction;

import ads.sjtu.edu.cn.Percolator.Conf;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import suportservice.service.SupportServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;


/**
 * Created by FD on 2018/3/28.
 */
public class SupportServiceClient implements SupportServer {
    static Logger logger = LoggerFactory.getLogger(SupportServiceClient.class);
    private static SupportServiceClient ourInstance = new SupportServiceClient();

    public static SupportServiceClient getInstance() {
        return ourInstance;
    }


    private SupportServiceClient() {
    }

    private SupportServer getSupportServer() {
        try {
            String rmiRrl = "rmi://" + Conf.MASTER_IP + ":20000/" + Conf.SUPPORT_SERVER_NAME;
            logger.info("connect support server url={}", rmiRrl);
            SupportServer supportServer = (SupportServer) Naming.lookup(rmiRrl);
            return supportServer;
        } catch (NotBoundException e) {
            logger.error(Throwables.getStackTraceAsString(e));
        } catch (MalformedURLException e) {
            logger.error(Throwables.getStackTraceAsString(e));
        } catch (RemoteException e) {
            logger.error(Throwables.getStackTraceAsString(e));
        }
        return null;
    }


    @Override
    public List<Long> getTimestamps(int num) throws RemoteException {
        SupportServer supportServer = getSupportServer();
        if (supportServer != null) {
            return supportServer.getTimestamps(num);
        }
        return Collections.emptyList();
    }

    @Override
    public void keepAlive(long id) throws RemoteException {
        SupportServer supportServer = getSupportServer();
        if (supportServer != null) {
            supportServer.keepAlive(id);
        }
    }

    @Override
    public boolean isAlive(long id) throws RemoteException {
        SupportServer supportServer = getSupportServer();
        return supportServer.isAlive(id);
    }
}
