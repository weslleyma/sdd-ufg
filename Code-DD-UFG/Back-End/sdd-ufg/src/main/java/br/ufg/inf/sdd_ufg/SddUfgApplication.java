package br.ufg.inf.sdd_ufg;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SddUfgApplication extends Application<SddUfgConfiguration> {
    public static void main(String[] args) throws Exception {
        new SddUfgApplication().run(args);
    }

    @Override
    public String getName() {
        return "sdd-ufg";
    }

    @Override
    public void initialize(Bootstrap<SddUfgConfiguration> bootstrap) {
        // nothing to do yet
    }
    
    @Override
    public void run(SddUfgConfiguration configuration, Environment environment) {
    	// Configure pretty print of json
    	ObjectMapper factory = environment.getObjectMapper();
        factory.enable(SerializationFeature.INDENT_OUTPUT);
        
    }

}