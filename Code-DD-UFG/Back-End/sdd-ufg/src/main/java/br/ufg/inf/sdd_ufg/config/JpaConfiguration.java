package br.ufg.inf.sdd_ufg.config;

import java.util.Properties;

import br.ufg.inf.sdd_ufg.SddUfgConfiguration;

import com.google.inject.persist.jpa.JpaPersistModule;

public class JpaConfiguration {

	public static JpaPersistModule createJpaPersistModule(SddUfgConfiguration conf) {
        Properties props = new Properties();
        props.put("javax.persistence.jdbc.url", conf.getDataSourceFactory().getUrl());
        props.put("javax.persistence.jdbc.user", conf.getDataSourceFactory().getUser());
        props.put("javax.persistence.jdbc.password", conf.getDataSourceFactory().getPassword());
        props.put("javax.persistence.jdbc.driver", conf.getDataSourceFactory().getDriverClass());
        JpaPersistModule jpaModule = new JpaPersistModule("Default");
        jpaModule.properties(props);
        return jpaModule;
    }
}
