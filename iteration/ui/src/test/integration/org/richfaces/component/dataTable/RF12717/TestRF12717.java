package org.richfaces.component.dataTable.RF12717;

import com.google.common.base.Function;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.warp.Activity;
import org.jboss.arquillian.warp.Inspection;
import org.jboss.arquillian.warp.Warp;
import org.jboss.arquillian.warp.WarpTest;
import org.jboss.arquillian.warp.jsf.AfterPhase;
import org.jboss.arquillian.warp.jsf.Phase;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.webapp30.WebAppDescriptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.integration.IterationDeployment;
import org.richfaces.shrinkwrap.descriptor.FaceletAsset;

import javax.annotation.Nullable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.net.URL;
import java.util.Iterator;

import static org.jboss.arquillian.graphene.Graphene.*;

@RunAsClient
@WarpTest
@RunWith(Arquillian.class)
public class TestRF12717 {

    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL contextPath;

    @FindBy(css = "form")
    private WebElement form;

    @FindBy(css = "table")
    private WebElement table;

    @Deployment
    public static WebArchive createDeployment() {
        IterationDeployment deployment = new IterationDeployment(TestRF12717.class, "4.2.3.Final");
        deployment.archive().addClass(SampleBean.class);
        deployment.archive().addClass(AbstractManagedBean.class);
        deployment.archive().addClass(DataRow.class);
        deployment.archive().addAsWebResource(new File("src/test/integration/org/richfaces/component/dataTable/RF12717/", "rf12717.xhtml"), "index.xhtml");

        deployment.webXml(new Function<WebAppDescriptor, WebAppDescriptor>() {
            @Override
            public WebAppDescriptor apply(@Nullable WebAppDescriptor input) {
                input
                        .createContextParam()
                        .paramName("javax.faces.PARTIAL_STATE_SAVING")
                        .paramValue("false")
                        .up();
                return input;
            }
        });


        return deployment.getFinalArchive();
    }

    @Test
    public void check_row_click() throws InterruptedException {
        browser.get(contextPath.toExternalForm());
        guardHttp(form.findElement(By.cssSelector("input[type='submit']"))).click();
        final WebElement firstRowLink = table.findElement(By.cssSelector("a"));

        Warp.initiate(new Activity() {
            @Override
            public void perform() {
                guardHttp(firstRowLink).click();
            }
        }).inspect(new Inspection() {
            private static final long serialVersionUID = 1L;

            @AfterPhase(Phase.RENDER_RESPONSE)
            public void verify_bean_executed() {
                Iterator<FacesMessage> iterator = FacesContext.getCurrentInstance().getMessages();
                Assert.assertTrue("No FacesMessages present", iterator.hasNext());
                FacesMessage msg = iterator.next();
                Assert.assertEquals("Clicked Data Row 1000", msg.getSummary());
            }
        });
    }

    private static void addIndexPage(IterationDeployment deployment) {
        FaceletAsset p = new FaceletAsset();
        p.xmlns("rich", "http://richfaces.org/iteration");
        p.xmlns("a4j", "http://richfaces.org/a4j");
        p.body("<h:form id='myForm'>");
        p.body("<h:commandButton id='show' value='Show Table' type='submit' action='#{iterationBean.show}' />");
        p.body("<rich:dataTable id='tableId' value='#{iterationBean.data}' var='bean' rows='3' rendered='#{!empty iterationBean.data}'> ");
        p.body("    <rich:column> ");
        p.body("        <f:facet name='header'> ");
        p.body("            <h:outputText value='Header' styleClass='tableHeader' /> ");
        p.body("        </f:facet> ");
        p.body("        <h:commandLink styleClass='selectLink' action='#{iterationBean.setSelectedValue(bean)}' value='Select #{bean}' immediate='true' /> ");
        p.body("    </rich:column> ");
        p.body("</rich:dataTable> ");
        p.body("</h:form>");

        deployment.archive().addAsWebResource(p, "index.xhtml");
    }

}
