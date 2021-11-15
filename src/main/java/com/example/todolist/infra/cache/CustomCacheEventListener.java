package com.example.todolist.infra.cache;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;


@Slf4j
public class CustomCacheEventListener implements CacheEventListener {


    @Override
    public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
        log.info("Remove from:{}, Element:{}", ehcache.getName(), element.toString());
    }

    @Override
    public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {
        log.info("Put into:{}, Element:{}", ehcache.getName(), element.toString());
    }

    @Override
    public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {
        log.info("Update into:{}, Element:{}", ehcache.getName(), element.toString());
    }

    @Override
    public void notifyElementExpired(Ehcache ehcache, Element element) {
        log.info("Expired to:{}, Element:{}", ehcache.getName(), element.toString());

    }

    @Override
    public void notifyElementEvicted(Ehcache ehcache, Element element) {
        log.info("Evicted to:{}, Element:{}", ehcache.getName(), element.toString());

    }

    @Override
    public void notifyRemoveAll(Ehcache ehcache) {
        log.info("Evicted All to:{}, Element:{}", ehcache.getName());

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return null;
    }

    @Override
    public void dispose() {
        log.info("dispose");
    }
}
