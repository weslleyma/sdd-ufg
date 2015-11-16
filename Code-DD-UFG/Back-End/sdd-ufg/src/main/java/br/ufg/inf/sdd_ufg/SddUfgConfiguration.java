package br.ufg.inf.sdd_ufg;

import io.dropwizard.db.DataSourceFactory;

import com.yammer.dropwizard.config.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SddUfgConfiguration extends Configuration {
 
	@Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
	
	@JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
	
	@JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}
