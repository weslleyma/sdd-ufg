package br.ufg.inf.sdd_ufg;

import br.ufg.inf.sdd_ufg.config.JpaConfiguration;
import br.ufg.inf.sdd_ufg.dao.ClazzDao;
import br.ufg.inf.sdd_ufg.dao.ClazzIntentDao;
import br.ufg.inf.sdd_ufg.dao.ClazzScheduleDao;
import br.ufg.inf.sdd_ufg.dao.CourseDao;
import br.ufg.inf.sdd_ufg.dao.DistributionProcessDao;
import br.ufg.inf.sdd_ufg.dao.GradeDao;
import br.ufg.inf.sdd_ufg.dao.KnowledgeGroupDao;
import br.ufg.inf.sdd_ufg.dao.KnowledgeLevelDao;
import br.ufg.inf.sdd_ufg.dao.TeacherDao;
import br.ufg.inf.sdd_ufg.dao.UserDao;
import br.ufg.inf.sdd_ufg.jpa.ClazzDaoJpa;
import br.ufg.inf.sdd_ufg.jpa.ClazzIntentDaoJpa;
import br.ufg.inf.sdd_ufg.jpa.ClazzScheduleDaoJpa;
import br.ufg.inf.sdd_ufg.jpa.CourseDaoJpa;
import br.ufg.inf.sdd_ufg.jpa.DistributionProcessDaoJpa;
import br.ufg.inf.sdd_ufg.jpa.GradeDaoJpa;
import br.ufg.inf.sdd_ufg.jpa.KnowledgeGroupDaoJpa;
import br.ufg.inf.sdd_ufg.jpa.KnowledgeLevelDaoJpa;
import br.ufg.inf.sdd_ufg.jpa.TeacherDaoJpa;
import br.ufg.inf.sdd_ufg.jpa.UserDaoJpa;
import br.ufg.inf.sdd_ufg.resource.ClazzIntentResource;
import br.ufg.inf.sdd_ufg.resource.ClazzResource;
import br.ufg.inf.sdd_ufg.resource.ClazzScheduleResource;
import br.ufg.inf.sdd_ufg.resource.CourseResource;
import br.ufg.inf.sdd_ufg.resource.DistributionProcessResource;
import br.ufg.inf.sdd_ufg.resource.GradeResource;
import br.ufg.inf.sdd_ufg.resource.KnowledgeGroupResource;
import br.ufg.inf.sdd_ufg.resource.KnowledgeLevelResource;
import br.ufg.inf.sdd_ufg.resource.SessionResource;
import br.ufg.inf.sdd_ufg.resource.TeacherResource;
import br.ufg.inf.sdd_ufg.resource.UserResource;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;


public class SddUfgApplication extends Service<SddUfgConfiguration> {
    public static void main(String[] args) throws Exception {
        new SddUfgApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<SddUfgConfiguration> bootstrap) {
        bootstrap.setName("sdd-ufg");
    }
    
    private Injector createInjector(final SddUfgConfiguration conf) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ClazzDao.class).to(ClazzDaoJpa.class);
                bind(ClazzIntentDao.class).to(ClazzIntentDaoJpa.class);
                bind(ClazzScheduleDao.class).to(ClazzScheduleDaoJpa.class);
                bind(CourseDao.class).to(CourseDaoJpa.class);
                bind(DistributionProcessDao.class).to(DistributionProcessDaoJpa.class);
                bind(GradeDao.class).to(GradeDaoJpa.class);
                bind(KnowledgeGroupDao.class).to(KnowledgeGroupDaoJpa.class);
                bind(KnowledgeLevelDao.class).to(KnowledgeLevelDaoJpa.class);
                bind(TeacherDao.class).to(TeacherDaoJpa.class);
                bind(UserDao.class).to(UserDaoJpa.class);
            }
        }, JpaConfiguration.createJpaPersistModule(conf));
    }
    
    
    @Override
    public void run(SddUfgConfiguration configuration, Environment environment) {
    	Injector injector = createInjector(configuration);
        
    	
    	environment.addResource(injector.getInstance(UserResource.class));
    	environment.addResource(injector.getInstance(CourseResource.class));
    	environment.addResource(injector.getInstance(ClazzResource.class));
    	environment.addResource(injector.getInstance(ClazzIntentResource.class));
    	environment.addResource(injector.getInstance(ClazzScheduleResource.class));
    	environment.addResource(injector.getInstance(DistributionProcessResource.class));
    	environment.addResource(injector.getInstance(GradeResource.class));
    	environment.addResource(injector.getInstance(KnowledgeGroupResource.class));
    	environment.addResource(injector.getInstance(KnowledgeLevelResource.class));
    	environment.addResource(injector.getInstance(TeacherResource.class));
    	environment.addResource(injector.getInstance(SessionResource.class));
    	
    	environment.addFilter(injector.getInstance(PersistFilter.class), "/*");
        environment.addFilter(injector.getInstance(AuthenticationRequestFilter.class), "/*");
    }

}