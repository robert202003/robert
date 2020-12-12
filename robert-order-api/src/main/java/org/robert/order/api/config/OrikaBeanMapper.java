package org.robert.user.api.config;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Iterator;
import java.util.Map;

public class OrikaBeanMapper extends ConfigurableMapper implements ApplicationContextAware {

    private MapperFactory factory;
    private ApplicationContext applicationContext;

    public OrikaBeanMapper() {
        super(false);
    }

    @Override
    protected void configure(MapperFactory factory) {
        this.factory = factory;
        this.addAllSpringBeans(this.applicationContext);
    }

    @Override
    protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
    }


    private void addMapper(Mapper<?, ?> mapper) {
        this.factory.registerMapper(mapper);
    }

    public void addConverter(Converter<?, ?> converter) {
        this.factory.getConverterFactory().registerConverter(converter);
    }

    private void addAllSpringBeans(ApplicationContext applicationContext) {
        Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
        Iterator var3 = mappers.values().iterator();

        while(var3.hasNext()) {
            Mapper mapper = (Mapper)var3.next();
            this.addMapper(mapper);
        }

        Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
        Iterator var7 = converters.values().iterator();

        while(var7.hasNext()) {
            Converter converter = (Converter)var7.next();
            this.addConverter(converter);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.init();
    }

}
