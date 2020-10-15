package com.imi.dsbsocket.service;

import com.imi.dsbsocket.entity.DsbSocketDomain;
import com.imi.dsbsocket.repository.DsbSocketDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;


@Service
public class DsbSocketDomainService {

    private final DsbSocketDomainRepository dsbSocketDomainRepository;

    @Value("${socketio.port}")
    private String port;
    private String selfDomain;

    @Autowired
    public DsbSocketDomainService(DsbSocketDomainRepository dsbSocketDomainRepository) {
        this.dsbSocketDomainRepository = dsbSocketDomainRepository;
    }

    @PostConstruct
    public void init() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        selfDomain = hostAddress + ":" + port;
    }

    public Optional<DsbSocketDomain> findByDomain(String domain) {
        return dsbSocketDomainRepository.findById(domain);
    }

    public void addOneOrElseReset(String domain) {
        DsbSocketDomain dsbSocketDomain = findByDomain(domain).orElse(new DsbSocketDomain());
        dsbSocketDomain.setDomain(domain);
        dsbSocketDomain.setConnCount(0);
        dsbSocketDomainRepository.save(dsbSocketDomain);
    }


    public void deleteOne(String domain) {
        dsbSocketDomainRepository.deleteById(domain);
    }

    public void updateOne(DsbSocketDomain dsbSocketDomain) {
        dsbSocketDomainRepository.save(dsbSocketDomain);
    }

    public void addOneOrElseResetBySelf() {
        addOneOrElseReset(selfDomain);
    }

    public void deleteOneBySelf() {
        deleteOne(selfDomain);
    }

    public void updateOneBySelf(int connCount) {
        DsbSocketDomain dsbSocketDomain = new DsbSocketDomain();
        dsbSocketDomain.setDomain(selfDomain);
        dsbSocketDomain.setConnCount(connCount);
        updateOne(dsbSocketDomain);
    }
}
