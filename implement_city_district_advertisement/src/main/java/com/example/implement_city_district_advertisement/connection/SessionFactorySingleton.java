package com.example.implement_city_district_advertisement.connection;

import com.example.implement_city_district_advertisement.ImplementCityDistrictAdvertisementApplication;
import com.example.implement_city_district_advertisement.models.Advertisement;
import com.example.implement_city_district_advertisement.models.City;
import com.example.implement_city_district_advertisement.models.District;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class SessionFactorySingleton {
    private SessionFactorySingleton() {
    }

    private static class LazyHolder {
        static SessionFactory INSTANCE;

        static {
            var registry = new StandardServiceRegistryBuilder ()
                    .configure ()
                    .build ();
            INSTANCE = new MetadataSources ( registry )
                    .addAnnotatedClass(ImplementCityDistrictAdvertisementApplication.class)
                    .addAnnotatedClass(Advertisement.class)
                    .addAnnotatedClass(City.class)
                    .addAnnotatedClass(District.class)
                    .buildMetadata ()
                    .buildSessionFactory ();
        }
    }

    public static SessionFactory getInstance() {
        return LazyHolder.INSTANCE;
    }
}