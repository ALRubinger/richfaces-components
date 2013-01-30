package org.richfaces.component.dataTable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class SampleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<DataRow> data;

    public String getBaseResourcePath() {
        return "components/richDataTable/";
    }

    public String begin() {
        return "index.xhtml";
    }

    public String show() {
        /* Build data List */
        data = new ArrayList<DataRow>();
        DataRow row1 = new DataRow(1000L, "Green", new Date(), Arrays.asList("green", "grn"));
        DataRow row2 = new DataRow(1001L, "Blue", new Date(), Arrays.asList("blue", "b"));
        data.add(row1);
        data.add(row2);
        return "index.xhtml";
    }

    public String clear() {
        data = null;
        return "index.xhtml";
    }

    public String loadDataById(Long id) {
        createFacesMessage(null, null, "Clicked Data Row " + id, null);
        return "index.xhtml";
    }

    public List<DataRow> getData() {
        return data;
    }

    public void setData(List<DataRow> data) {
        this.data = data;
    }

    protected void createFacesMessage(FacesMessage.Severity severity, String clientId, String summary, String detail) {
        if (severity == null) {
            severity = FacesMessage.SEVERITY_INFO;
        }

        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }


}
