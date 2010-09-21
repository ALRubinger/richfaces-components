/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.richfaces.renderkit.util;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.AjaxClientBehavior;
import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.richfaces.renderkit.AjaxEventOptions;
import org.richfaces.renderkit.HtmlConstants;
/**
 * @author shura
 *         <p/>
 *         Some utilites for render AJAX components.
 */
public final class AjaxRendererUtils {

    public static final String AJAX_ABORT_ATTR = "ignoreDupResponses";
    public static final String AJAX_AREAS_RENDERED = "org.ajax4jsf.areas.rendered";
    public static final String AJAX_DELAY_ATTR = "requestDelay";

    /**
     * Name Javasript function for submit AJAX request
     */
    public static final String AJAX_FUNCTION_NAME = "RichFaces.ajax";

    public static final String AJAX_QUEUE_ATTR = "eventsQueue";
    public static final String AJAX_SINGLE_ATTR = "ajaxSingle";
    public static final String AJAX_SINGLE_PARAMETER_NAME = "ajaxSingle";

    public static final String ONBEGIN_ATTR_NAME = "onbegin";

    /**
     * Attribute for keep JavaScript function name for call after complete
     * request.
     */
    public static final String ONCOMPLETE_CONTENT_ID = "org.ajax4jsf.oncomplete";
    public static final String SIMILARITY_GROUPING_ID_ATTR = "similarityGroupingId";

    /**
     * Attribute for keep clientId of status component
     */
    public static final String STATUS_ATTR_NAME = "status";
    public static final String VALUE_ATTR = "value";

    public static final String QUEUE_ID_ATTRIBUTE = "queueId";

    private static final RendererUtils RENDERER_UTILS = RendererUtils.getInstance();
    
    /**
     * Static class - protect constructor
     */
    private AjaxRendererUtils() {
    }

    private static enum BehaviorEventOptionsData {
        begin {
            @Override
            public String getAttributeValue(AjaxClientBehavior behavior) {
                return behavior.getOnbegin();
            }
        },
        error {
            @Override
            public String getAttributeValue(AjaxClientBehavior behavior) {
                return behavior.getOnerror();
            }
        },
        queueId {
            @Override
            public String getAttributeValue(AjaxClientBehavior behavior) {
                return behavior.getQueueId();
            }
        },
        event {
            @Override
            public String getAttributeValue(AjaxClientBehavior behavior) {
                return behavior.getOnevent();
            }
        };

        public abstract String getAttributeValue(AjaxClientBehavior behavior);
    }

    /**
     * Build JavaScript onclick event for given component
     *
     * @param uiComponent  -
     *                     component for build event
     * @param facesContext
     * @return <code>StringBuffer</code> with Javascript code
     */
    public static StringBuffer buildOnClick(UIComponent uiComponent, FacesContext facesContext) {
        return buildOnClick(uiComponent, facesContext, false);
    }

    /**
     * Build JavaScript onclick event for given component
     *
     * @param uiComponent          -
     *                             component for build event
     * @param facesContext
     * @param omitDefaultActionUrl - default action URL is not encoded if parameter is true
     * @return <code>StringBuffer</code> with Javascript code
     */
    public static StringBuffer buildOnClick(UIComponent uiComponent, FacesContext facesContext,
                                            boolean omitDefaultActionUrl) {
        return buildOnEvent(uiComponent, facesContext, HtmlConstants.ONCLICK_ATTRIBUTE, omitDefaultActionUrl);
    }

    /**
     * Build JavaScript event for component
     *
     * @param uiComponent  -
     *                     component for build event
     * @param facesContext
     * @param eventName    -
     *                     name of event
     * @return <code>StringBuffer</code> with Javascript code
     */
    public static StringBuffer buildOnEvent(UIComponent uiComponent, FacesContext facesContext, String eventName) {
        return buildOnEvent(uiComponent, facesContext, eventName, false);
    }

    /**
     * Build JavaScript event for component
     *
     * @param uiComponent          -
     *                             component for build event
     * @param facesContext
     * @param eventName            -
     *                             name of event
     * @param omitDefaultActionUrl - default action URL is not encoded if parameter is true
     * @return <code>StringBuffer</code> with Javascript code
     */
    public static StringBuffer buildOnEvent(UIComponent uiComponent, FacesContext facesContext, String eventName,
                                            boolean omitDefaultActionUrl) {
        StringBuffer onEvent = new StringBuffer();

//      if (null != eventName) {
//          String commandOnEvent = (String) uiComponent.getAttributes().get(
//                  eventName);
//          if (commandOnEvent != null) {
//              onEvent.append(commandOnEvent);
//              onEvent.append(';');
//          }
//      }
//      JSFunction ajaxFunction = buildAjaxFunction(uiComponent, facesContext);
//      // Create formal parameter for non-input elements ???
//      // Link Control pseudo-object
//      // Options map. Possible options for function call :
//      // control - name of form control for submit.
//      // name - name for link control \
//      // value - value of control. - possible replace by parameters ?
//      // single true/false - submit all form or only one control.
//      // affected - array of element's ID for update on responce.
//      // oncomplete - function for call after complete request.
//      // status - id of request status component.
//      // parameters - map of parameters name/value for append on request.
//      // ..........
//      ajaxFunction.addParameter(buildEventOptions(facesContext, uiComponent,  omitDefaultActionUrl));
//
//      // appendAjaxSubmitParameters(facesContext, uiComponent, onEvent);
//      ajaxFunction.appendScript(onEvent);
//      if (uiComponent instanceof AjaxSupport) {
//          AjaxSupport support = (AjaxSupport) uiComponent;
//          if (support.isDisableDefault()) {
//              onEvent.append("; return false;");
//          }
//      }
//      LOG.debug(Messages.getMessage(Messages.BUILD_ONCLICK_INFO, uiComponent
//              .getId(), onEvent.toString()));
        return onEvent;
    }

    public static AjaxEventOptions buildEventOptions(FacesContext facesContext, UIComponent component) {
        return buildEventOptions(facesContext, component, null);
    }

    public static AjaxEventOptions buildEventOptions(FacesContext facesContext, UIComponent component,
                                                     AjaxClientBehavior ajaxBehavior) {
        AjaxEventOptions ajaxEventOptions = new AjaxEventOptions();
        Map<String, Object> parametersMap = RENDERER_UTILS.createParametersMap(facesContext, component);
        String ajaxStatusName = getAjaxStatus(component);

        if (ajaxBehavior != null) {
            ajaxStatusName = (ajaxBehavior.getStatus() != null) ? ajaxBehavior.getStatus() : ajaxStatusName;
            appenAjaxBehaviorOptions(ajaxBehavior, ajaxEventOptions);
        } else {
            appendComponentOptions(facesContext, component, ajaxEventOptions);
        }

        if ((ajaxStatusName != null) && (ajaxStatusName.length() != 0)) {
            ajaxEventOptions.set(STATUS_ATTR_NAME, ajaxStatusName);
        }

        if (!parametersMap.isEmpty()) {
            ajaxEventOptions.getParameters().putAll(parametersMap);
        }

        return ajaxEventOptions;
    }

    private static boolean isNotEmpty(String value) {
        return (value != null) && (value.length() != 0);
    }

    private static void appenAjaxBehaviorOptions(AjaxClientBehavior behavior, AjaxEventOptions ajaxEventOptions) {
        for (BehaviorEventOptionsData optionsData : BehaviorEventOptionsData.values()) {
            String eventHandlerValue = optionsData.getAttributeValue(behavior);

            if (isNotEmpty(eventHandlerValue)) {
                ajaxEventOptions.set(optionsData.toString(), eventHandlerValue);
            }
        }
    }

    private static void appendComponentOptions(FacesContext facesContext, UIComponent component,
                                               AjaxEventOptions ajaxEventOptions) {
        String behaviorName = "begin";
        HandlersChain handlersChain = new HandlersChain(facesContext, component);
        String inlineHandler = getAjaxOnBegin(component);

        handlersChain.addInlineHandlerAsValue(inlineHandler);
        handlersChain.addBehaviors(behaviorName);

        String handlerScript = handlersChain.toScript();

        if (isNotEmpty(handlerScript)) {
            ajaxEventOptions.set(behaviorName, handlerScript);
        }

        String queueId = getQueueId(component);
        if (isNotEmpty(queueId)) {
            ajaxEventOptions.set(QUEUE_ID_ATTRIBUTE, queueId);
        }
        
        ajaxEventOptions.set("incId", "1");
    }

//  public static AjaxEventOptions buildEventOptions(FacesContext facesContext,
//          UIComponent uiComponent, Map<String, Object> params) {
//
//      return buildEventOptions(facesContext, uiComponent, params, false);
//  }

    /**
     * @param facesContext
     * @param uiComponent
     * @return
     */
//  public static Map<String, Object> buildEventOptions(FacesContext facesContext,
//          UIComponent uiComponent, Map<String, Object> params, boolean omitDefaultActionUrl) {
//      String clientId = uiComponent.getClientId(facesContext);
//      Map<String, Object> componentAttributes = uiComponent.getAttributes();
//      Map<String, Object> options = new HashMap<String, Object>();
//
//      UIComponent nestingContainer = (UIComponent) findAjaxContainer(
//              facesContext, uiComponent);
//      String containerClientId = nestingContainer.getClientId(facesContext);
//      if (containerClientId != null && !AjaxViewRoot.ROOT_ID.equals(containerClientId)) {
//          options.put("containerId", containerClientId);
//      }
//
//      Map<String, Object> parameters = new HashMap<String, Object>();
//      UIComponent targetComponent = (uiComponent instanceof AjaxSupport)?uiComponent.getParent():uiComponent;
//      // UIForm form = getNestingForm(uiComponent);
//      // "input" - if assigned to html input element.
//      boolean input = targetComponent instanceof EditableValueHolder;
//      // Action component - button etc.
////        boolean action = targetComponent instanceof ActionSource;
//
//      boolean ajaxSingle = Boolean.TRUE.equals(componentAttributes
//              .get(AJAX_SINGLE_ATTR));
//      // For input components in single mode or without form submit input
//      // control )
//      if (ajaxSingle ) {
//          parameters.put(AJAX_SINGLE_PARAMETER_NAME, targetComponent.getClientId(facesContext));
//          // options.put("single", JSReference.TRUE);
//          if (input) {
//              options.put("control", JSReference.THIS);
//          }
//      }
//      // Control value for submit
//      String controlName;
//      Object controlValue;
//      // TODO - make compatible with JSF RI/MyFaces ? use submittedValue ( if
//      // any ) for UIInput, converted value for ValueHolder.
//      controlName = clientId;
//      controlValue = clientId;
//      parameters.put(controlName, controlValue);
//      AjaxContext ajaxContext = AjaxContext.getCurrentInstance(facesContext);
//
//      String ajaxActionURL = ajaxContext.getAjaxActionURL(facesContext);
//      if (omitDefaultActionUrl) {
//          UIComponent form = getNestingForm(uiComponent);
//          if (form != null && !RENDERER_UTILS.isBooleanAttribute(form, "ajaxSubmit")) {
//              if (RENDERER_UTILS.getActionUrl(facesContext).equals(ajaxActionURL)) {
//                  ajaxActionURL = null;
//              }
//          }
//      }
//
//      if (ajaxActionURL != null) {
//          // Setup action URL. For portlet environment, it will be different from
//          // page.
//          options.put("actionUrl", ajaxActionURL);
//      }
//
//      // Add application-wide Ajax parameters
//      parameters.putAll(ajaxContext.getCommonAjaxParameters());
//      // add child parameters
//      appendParameters(facesContext, uiComponent, parameters);
//
//      if (params != null) {
//          parameters.putAll(params);
//      }
//
//      if (!parameters.isEmpty()) {
//          options.put("parameters", parameters);
//      }
//      // parameter to render only current list of areas.
////        if (isAjaxLimitToList(uiComponent)) {
////            Set<? extends Object> ajaxAreas = getAjaxAreas(uiComponent);
////            Set<String> areasIds = new HashSet<String>();
////            if (null != ajaxAreas) {
////                for (Iterator<? extends Object> iter = ajaxAreas.iterator(); iter.hasNext();) {
////                    String id = (String) iter.next();
////                    UIComponent comp = RendererUtils.getInstance().
////                        findComponentFor(uiComponent, id);
////                    if (null != comp) {
////                        areasIds.add(comp.getClientId(facesContext));
////                    } else {
////                        areasIds.add(id);
////                    }
////                }
////            }
////            options.put("affected", areasIds);
////        }
//      String oncomplete = getAjaxOncomplete(uiComponent);
//      if (null != oncomplete) {
//          options.put(ONCOMPLETE_ATTR_NAME, buildAjaxOncomplete(oncomplete));
//      }
//
//      String beforeupdate = getAjaxOnBeforeDomUpdate(uiComponent);
//      if (null != beforeupdate) {
//          options.put(ONBEFOREDOMUPDATE_ATTR_NAME, buildAjaxOnBeforeDomUpdate(beforeupdate));
//      }
//
//
//      String status = getAjaxStatus(uiComponent);
//      if (null != status) {
//          options.put("status", status);
//      }
//      String queue = (String) componentAttributes.get(AJAX_QUEUE_ATTR);
//      String implicitQueue = null;
//
//      Integer requestDelay = (Integer) componentAttributes
//              .get(AJAX_DELAY_ATTR);
//      if (null != requestDelay && requestDelay.intValue() > 0) {
//          options.put(AJAX_DELAY_ATTR, requestDelay);
//          if (null == queue) {
//              implicitQueue = clientId;
//          }
//      }
//      Boolean ignoreDupResponses = (Boolean) componentAttributes
//              .get(AJAX_ABORT_ATTR);
//      if (null != ignoreDupResponses && ignoreDupResponses.booleanValue()) {
//          options.put(AJAX_ABORT_ATTR, JSReference.TRUE);
//          if (null == queue) {
//              implicitQueue = clientId;
//          }
//      }
//
//      if (null != queue) {
//          options.put(AJAX_QUEUE_ATTR, queue);
//      } else if (implicitQueue != null) {
//          options.put("implicitEventsQueue", clientId);
//      }
//
//      ExternalContext externalContext = facesContext.getExternalContext();
//      String namespace = externalContext.encodeNamespace("");
//      if (namespace != null && namespace.length() != 0) {
//          options.put("namespace", namespace);
//      }
//
//      String similarityGroupingId = (String) componentAttributes.get(SIMILARITY_GROUPING_ID_ATTR);
//      if (similarityGroupingId == null || similarityGroupingId.length() == 0) {
//          similarityGroupingId = clientId;
//      } else {
//          similarityGroupingId = externalContext.encodeNamespace(similarityGroupingId);
//      }
//
//      options.put(SIMILARITY_GROUPING_ID_ATTR, similarityGroupingId);
//
//      // request timeout.
//      Integer timeout = (Integer) componentAttributes.get("timeout");
//      if (null != timeout && timeout.intValue() > 0) {
//          options.put("timeout", timeout);
//      }
//      // Encoding for requests
//      String encoding = (String) componentAttributes.get("encoding");
//      if (null != encoding) {
//          options.put("encoding", encoding);
//      }
//      return options;
//  }
//  /**
//   * Create call to Ajax Submit function with first two parameters
//   *
//   * @param uiComponent
//   * @param facesContext
//   * @param functionName
//   * @return
//   */
//  public static JSFunction buildAjaxFunction(UIComponent uiComponent,
//          FacesContext facesContext) {
//      JSFunction ajaxFunction = buildAjaxFunction(uiComponent, facesContext,
//              AJAX_FUNCTION_NAME);
//      // client-side script must have reference to event-enabled object.
//      ajaxFunction.addParameter(new JSReference("event"));
//      return ajaxFunction;
//  }

    /**
     * Create call to Ajax Submit function with first two parameters
     *
     * @param facesContext
     * @param uiComponent
     * @param functionName
     * @return
     */
    public static JSFunction buildAjaxFunction(FacesContext facesContext, UIComponent uiComponent,
                                               String functionName) {
        JSFunction ajaxFunction = new JSFunction(functionName);

        ajaxFunction.addParameter(uiComponent.getClientId(facesContext));
        ajaxFunction.addParameter(JSReference.EVENT);

        return ajaxFunction;
    }

    /**
     * Append common parameters ( array of affected areas, status area id, on
     * complete function ) to JavaScript event string.
     *
     * @param uiComponent
     * @param onClick -
     *            buffer with JavaScript code eg... AJAX.Submit(form,this
     */

    // public static void appendAjaxSubmitParameters(FacesContext facesContext,
    // UIComponent uiComponent, StringBuffer onClick)
    // {
    // Set ajaxAreas = getAjaxAreas(uiComponent);
    // onClick.append(',');
    // // parameter to render only current list of areas.
    // if (isAjaxLimitToList(uiComponent) && ajaxAreas != null &&
    // ajaxAreas.size() > 0)
    // {
    // onClick.append('[');
    // Iterator areas = ajaxAreas.iterator();
    // boolean first = true;
    // while (areas.hasNext())
    // {
    // String element = (String) areas.next();
    // UIComponent component = uiComponent.findComponent(element);
    // if (null != component)
    // {
    // if (!first)
    // {
    // onClick.append(',');
    // }
    // else
    // {
    // first = false;
    // }
    // onClick.append('\'');
    // onClick.append(component.getClientId(facesContext));
    // onClick.append('\'');
    // }
    // }
    // onClick.append("]");
    // }
    // else
    // {
    // onClick.append("null");
    // }
    // // insert id of request status element.
    // onClick.append(',');
    // String status = getAjaxStatus(uiComponent);
    // if (null != status)
    // {
    // onClick.append('\'').append(status).append('\'');
    // }
    // else
    // {
    // onClick.append("null");
    // }
    // // insert function name for call after completed request
    // onClick.append(',');
    // String oncomplete = getAjaxOncomplete(uiComponent);
    // if (null != oncomplete)
    // {
    // onClick.append(oncomplete);
    // }
    // else
    // {
    // onClick.append("null");
    // }
    //
    // }

    /**
     * Get status area Id for given component.
     *
     * @param component
     * @return clientId of status area, or <code>null</code>
     */
    public static String getAjaxStatus(UIComponent component) {
        String statusId;

        if (component instanceof AjaxComponent) {
            statusId = ((AjaxComponent) component).getStatus();
        } else {
            statusId = (String) component.getAttributes().get(STATUS_ATTR_NAME);
        }

        return statusId;

//      if (null != statusId) {
//          UIComponent status = RendererUtils.getInstance().
//              findComponentFor(component, statusId);
//
//          if (null != status) {
//              statusId = status
//                      .getClientId(FacesContext.getCurrentInstance());
//          } else {
//              LOG.warn(Messages.getMessage(
//                      Messages.AJAX_STATUS_COMPONENT_NOT_FOWND_WARNING,
//                      component.getId()));
//          }
//      }
//      return statusId;
    }

    public static String getQueueId(UIComponent component) {
        return (String) component.getAttributes().get(QUEUE_ID_ATTRIBUTE);
    }
    
    public static JSFunctionDefinition buildAjaxOncomplete(String body) {
        JSFunctionDefinition function = new JSFunctionDefinition("request", "event", "data");

        function.addToBody(body);

        return function;
    }

    public static JSFunctionDefinition buildAjaxOnBeforeDomUpdate(String body) {
        JSFunctionDefinition function = new JSFunctionDefinition("request", "event", "data");

        function.addToBody(body);

        return function;
    }

    //TODO nick - refactor - remove this method?
    public static String getAjaxOnBegin(UIComponent component) {
        if (component instanceof AjaxComponent) {
            return ((AjaxComponent) component).getOnbegin();
        }

        return (String) component.getAttributes().get(ONBEGIN_ATTR_NAME);
    }

    protected static String getAjaxActionUrl(FacesContext facesContext) {
        return AjaxContext.getCurrentInstance(facesContext).getAjaxActionURL(facesContext);
    }

    /**
     * @param facesContext
     * @return
     */
    public static boolean isAjaxRequest(FacesContext facesContext) {
        return AjaxContext.getCurrentInstance(facesContext).isAjaxRequest();
    }

    /**
     * @param facesContext
     * @param component
     */
    public static void addRegionsFromComponent(UIComponent component, FacesContext facesContext) {
        AjaxContext.getCurrentInstance(facesContext).addRegionsFromComponent(component);
    }


}