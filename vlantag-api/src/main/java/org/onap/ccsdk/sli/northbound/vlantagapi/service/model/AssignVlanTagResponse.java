package org.onap.ccsdk.sli.northbound.vlantagapi.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class AssignVlanTagResponse {

    private @Valid List<AssignVlanTagResponseOutput> output = new ArrayList<AssignVlanTagResponseOutput>();
    private @Valid Integer errorCode = null;
    private @Valid String errorMessage = null;

    public AssignVlanTagResponse() {
    }

    /**
     **/
    public AssignVlanTagResponse output(List<AssignVlanTagResponseOutput> output) {
        this.output = output;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("output")
    public List<AssignVlanTagResponseOutput> getOutput() {
        return output;
    }

    public void setOutput(List<AssignVlanTagResponseOutput> output) {
        this.output = output;
    }

    /**
     **/
    public AssignVlanTagResponse errorCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("error-code")
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     **/
    public AssignVlanTagResponse errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("error-message")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssignVlanTagResponse assignVlanTagResponse = (AssignVlanTagResponse) o;
        return Objects.equals(errorCode, assignVlanTagResponse.errorCode)
                && Objects.equals(errorMessage, assignVlanTagResponse.errorMessage)
                && Objects.equals(output, assignVlanTagResponse.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(output, errorCode, errorMessage);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AssignVlanTagResponse {\n");

        sb.append("    output: ").append(toIndentedString(output)).append("\n");
        sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
        sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
