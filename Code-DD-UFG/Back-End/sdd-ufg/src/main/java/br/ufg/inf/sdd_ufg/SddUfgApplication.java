package br.ufg.inf.sdd_ufg;

import br.ufg.inf.sdd_ufg.config.JpaConfiguration;
import br.ufg.inf.sdd_ufg.dao.UserDao;
import br.ufg.inf.sdd_ufg.jpa.UserDaoJpa;
import br.ufg.inf.sdd_ufg.resource.UserResource;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;


public class SddUfgApplication extends Service<SddUfgConfiguration> {
    public static void main(String[] args) throws Exception {
        new SddUfgApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<SddUfgConfiguration> bootstrap) {
        bootstrap.setName("mr-api");
    }
    
    private Injector createInjector(final SddUfgConfiguration conf) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(UserDao.class).to(UserDaoJpa.class);
            }
        }, JpaConfiguration.createJpaPersistModule(conf));
    }
    
    
    @Override
    public void run(SddUfgConfiguration configuration, Environment environment) {
    	Injector injector = createInjector(configuration);
        
    	environment.addResource(injector.getInstance(UserResource.class));
    }

}