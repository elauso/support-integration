FROM openjdk:8-jdk-alpine

RUN apk add vim
RUN apk add net-tools
RUN apk add curl

ENV KARAF_VERSION=4.0.10

RUN wget http://www-us.apache.org/dist/karaf/${KARAF_VERSION}/apache-karaf-${KARAF_VERSION}.tar.gz; \
    mkdir /opt/karaf; \
    tar --strip-components=1 -C /opt/karaf -xzf apache-karaf-${KARAF_VERSION}.tar.gz; \
    rm apache-karaf-${KARAF_VERSION}.tar.gz; \
    mkdir /deploy; \
    mkdir /config; \
    sed -i 's/^\(felix\.fileinstall\.dir\s*=\s*\).*$/\1\/deploy/' /opt/karaf/etc/org.apache.felix.fileinstall-deploy.cfg

COPY org.ops4j.pax.url.mvn.cfg /opt/karaf/etc/
COPY org.apache.karaf.features.cfg /opt/karaf/etc/
COPY config.properties /opt/karaf/etc/

VOLUME ["/deploy"]
VOLUME ["/config"]
EXPOSE 1099 8101 44444 8080
ENTRYPOINT ["/opt/karaf/bin/karaf"]