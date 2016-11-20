package com.iliani14.pg6100;


import com.iliani14.pg6100.api.QuizRestImpl;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;

/**
 * Created by anitailieva on 15/11/2016.
 */
public class QuizApplication extends Application<QuizConfiguration> {

    public static void main(String[] args) throws Exception {
        new QuizApplication().run(args);
    }

    @Override
    public String getName() {
        return "game";
    }

    @Override
    public void initialize(Bootstrap<QuizConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", null, "a"));
        bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "b"));
        bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/fonts", null, "c"));
        bootstrap.addBundle(new AssetsBundle("/assets/images", "/images", null, "d"));
        bootstrap.addBundle(new AssetsBundle("/assets/lang", "/lang", null, "e"));
        bootstrap.addBundle(new AssetsBundle("/assets/lib", "/lib", null, "f"));
    }

    @Override
    public void run(QuizConfiguration configuration, Environment environment) {
        environment.jersey().setUrlPattern("/game/api/*");
        environment.jersey().register(new QuizRestImpl());

        //swagger
        environment.jersey().register(new ApiListingResource());

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("0.0.1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/game");
        beanConfig.setResourcePackage("com.iliani14.pg6100");
        beanConfig.setScan(true);

        environment.jersey().register(new io.swagger.jaxrs.listing.ApiListingResource());
        environment.jersey().register(new io.swagger.jaxrs.listing.SwaggerSerializers());

    }
}