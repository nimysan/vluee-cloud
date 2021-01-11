package com.vluee.maven;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.*;


@Mojo(name = "generate", defaultPhase = LifecyclePhase.PACKAGE)
public class VlueeYamlMojo extends AbstractMojo {

    public static final String ENCODING = "UTF-8";


    @Parameter(name = "networkName", defaultValue = "aistore-network")
    private String networkName;

    @Parameter(name = "namespace", defaultValue = "aistore")
    private String namespace;

    @Parameter(property = "docker.export.port")
    private String port;


    @Parameter(name = "template")
    private String template;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    /**
     * 根据参数创建options
     *
     * @return
     */
    private Options composeOptions() {
        getLog().info("--- " + networkName);
        String artifactId = project.getArtifactId();
        Options options = new Options(artifactId, port, namespace + "/" + artifactId, networkName);
        getLog().info("Configuration is: " + options);
        return options;
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        String artifactId = project.getArtifactId();
        getLog().info("--- " + project.getBasedir() + " - " + artifactId);

        Configuration config = new Configuration(Configuration.VERSION_2_3_0);
        try {
            String outputDir = project.getBasedir() + "/target/";
            getLog().info("Template work dir: " + outputDir);

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputDir + artifactId + "-service.yaml"), ENCODING));
            getTemplate(config).process(composeOptions(), new PrintWriter(out));
            out.flush();
            out.close();
        } catch (Exception e) {
            getLog().error("Failed", e);
            throw new MojoExecutionException("Failed to generate deploy descriptor", e);
        }
    }

    //单独测试
    public static void main(String[] args) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setClassForTemplateLoading(VlueeYamlMojo.class, "");
        Template template = configuration.getTemplate("template.flt", ENCODING);
        template.process(new Options("1", "2", "3", "swarm-network"), new PrintWriter(System.out));
    }

    private Template getTemplate(Configuration configuration) throws IOException {
        if (StringUtils.isBlank(template)) {
            configuration.setClassForTemplateLoading(VlueeYamlMojo.class, "");
            return configuration.getTemplate("template.flt", ENCODING);
        } else {
            return configuration.getTemplate(project.getBasedir() + "/src/main/resources/" + template, ENCODING);
        }
    }
}