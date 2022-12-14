FROM maven:3.6.1-jdk-11 AS builder

RUN apt-get update && apt-get install -y --no-install-recommends \
		curl \
		python \
	&& rm -rf /var/lib/apt/lists/*

# Download & install BLAST
RUN mkdir /opt/blast \
      && curl ftp://ftp.ncbi.nlm.nih.gov/blast/executables/blast+/2.2.30/ncbi-blast-2.2.30+-x64-linux.tar.gz \
      | tar -zxC /opt/blast --strip-components=1

ENV PATH /opt/blast/bin:$PATH

ADD *.sh /usr/local/bin/

COPY settings.template.xml /root/.m2/settings.xml

RUN /usr/local/bin/run_replace.sh \
    && mkdir -p /genotyphi/build/resources

COPY . /usr/src/mymaven/

WORKDIR /usr/src/mymaven/

RUN mvn clean package \
    && mv /usr/src/mymaven/build/genotyphi.jar /genotyphi/build/

FROM openjdk:11-jre

RUN mkdir -p /opt/blast/bin

COPY --from=builder /opt/blast/bin/blastn /opt/blast/bin

ENV PATH /opt/blast/bin:$PATH

COPY --from=builder /genotyphi/build/ /genotyphi/

RUN mkdir /data

WORKDIR /data

ENTRYPOINT ["java","-jar","/genotyphi/genotyphi.jar"]