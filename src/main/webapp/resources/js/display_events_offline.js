/*
 * JavaScript file for C2I button bindings for button click events and dropdown menu change events. 
 * This JavaScript file is designed using the Singleton design pattern with public and private properties
 * and methods. The public properties and methods can be accessed from anywhere using the cncEvents variable
 */

var cncEvents = function () {

    // Private methods and properties
    var IsSearchById = false;
    var requestTypePost = "POST";
    var requestTypeGet = "GET";
    var receiveTypeJson = "JSON";
    var receiveTypeText = "TEXT";
    var searchOperation = "SEARCH";
    var deleteOperation = "DELETE";
    var saveOperation = "SAVE";
    var updateOperation = "UPDATE";
    var configOperation = "CONFIG";
    var acceptOperation = "ACCEPT";
    var assignOperation = "ASSIGN";
    var resolveOperation = "RESOLVE";
    var textfield = "TEXTFIELD";
    var dropdown = "DROPDOWN";

    // Dropdown and text field population functions
    function populateNetworkDropdown() {

        var requestUrl = "/cnc/network/getAllNetworks";
        var sendData = {};

        $("#deviceNetworkSearchDropdown").append($("<option>", {
            text: "Select a network",
            value: ""
        }));

        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData, requestTypeGet,
                receiveTypeJson, function (response, status, error) {
                    $.each(response.networks, function (index, value) {
                        $("#deviceNetworkSearchDropdown").append(
                                $("<option>", {
                                    text: value.networkId,
                                    value: value.networkId
                                }));
                    });
                });
    }

    function populateVideoCameraDropdown() {

        var requestUrl = "/cnc/device/getVideoCameras";
        var sendData = {};

        $("#videoCameraDropdown").append($("<option>", {
            text: "Select a video camera",
            value: ""
        }));

        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData, requestTypeGet,
                receiveTypeJson, function (response, status, error) {
                    $.each(response.devices, function (index, value) {
                        $("#videoCameraDropdown").append($("<option>", {
                            text: value.deviceId,
                            value: value.videoStreamLink
                        }));
                    });
                });
    }

    function setUserDropdownData(dropdown) {

        var requestUrl = "/cnc/user/getAllUsers";
        var sendData = {};
        var dropdownToPopulate;

        if (dropdown == "update") {
            dropdownToPopulate = "#updateUserId";
        } else if (dropdown == "delete") {
            dropdownToPopulate = "#deleteUserIdDropdown";
        }

        $(dropdownToPopulate).append($("<option>", {
            text: "Select a username",
            value: ""
        }));

        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData, requestTypeGet,
                receiveTypeJson, function (response, status, error) {
                    $.each(response.users, function (index, value) {
                        $(dropdownToPopulate).append($("<option>", {
                            text: value.userId,
                            value: value.userId
                        }));
                    });
                });
    }

    function setFieldUserDropdownData() {

        var fieldUser = "FIELD_USER";
        var requestUrl = "/cnc/user/searchByRole";
        var sendData = {
            userRoleSearchParam: fieldUser
        };
        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData, requestTypeGet,
                receiveTypeJson, function (response, status, error) {
                    populateFieldUserDropdown(response);
                });
    }

    function setAssignedAlertDropdownData() {

        var username = cncDisplay.getUsername();
        var requestUrl = "/cnc/alert/searchAssignedAlerts";
        var sendData = {
            usernameParam: username
        };
        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData, requestTypeGet,
                receiveTypeJson, function (response, status, error) {
                    populateAssignedAlertDropdown(response);
                });
    }

    function populateAssignedAlertDropdown(result) {

        $("#alert-resolve-result-div").html("");
        if (result.found == true) {
            var alertData;
            $.each(result.alerts,
                    function (index, value) {
                        var dateReceived = new Date(value.alertAssignedDate);
                        alertData = dateReceived + " Device Id: "
                                + value.alertDeviceId;
                        $("#assignedAlertDeviceId").append($("<option>", {
                            text: alertData,
                            value: value.alertDeviceId
                        }));
                        $("#assignedAlertDeviceId option").css(
                                "background-color", "yellow");
                    });

        } else {
            $("#alert-resolve-result-div").addClass("partial-success")
            $("#alert-resolve-result-div").html(
                    "No alerts have been assigned at this moment!!!");
        }

    }

    function populateFieldUserDropdown(result) {

        $("#assignedTo").append($("<option>", {
            text: "Select a user",
            value: ""
        }));

        $.each(result.users, function (index, value) {

            var fullName = value.firstName + " " + value.lastName;

            $("#assignedTo").append($("<option>", {
                text: fullName,
                value: value.userId
            }));

        });
    }

    function populateUserUpdateForm(result) {
        $("#updateFirstName").val(result.user.firstName);
        $("#updateLastName").val(result.user.lastName);
        $("#updatePassword").val(result.user.password);
        $("#updateUserRole").val(result.user.userRole);
        $("#updateUserStatus").val(result.user.userStatus);
    }

    // End of dropdown and text field population functions

    // Function to notify user of an emty text field or drop
    // down option
    function displayEmptyFieldMessage(resultDiv, fieldType) {

        $(resultDiv).html("");
        var message;
        if (fieldType == "DROPDOWN") {
            message = "Please select a value from the dropdown to perform the required operation";

        } else if (fieldType == "TEXTFIELD") {
            message = "Please enter a value in the required text field to perform the required operation";

        }

        $(resultDiv).addClass("error");
        $(resultDiv).html(message);
    }

    function populateDeviceSearchResultTable(value, search_device_result_html) {

        search_device_result_html += "<tr><td>" + value.deviceId + "</td><td>"
                + value.networkId + "</td><td>" + value.deviceStatus
                + "</td><td>" + value.deviceType + "</td><td>"
                + value.deviceBattery + "</td><td>" + value.deviceSensitivity
                + "</td><td>" + value.deviceLinkQualityIndicator + "</td><td>"
                + value.deviceSignalStrength + "</td></tr>";

        return search_device_result_html;
    }

    function populateUserSearchResultTable(value, search_user_result_html) {

        search_user_result_html += "<tr><td>" + value.userId + "</td><td>"
                + value.firstName + "</td><td>" + value.lastName + "</td><td>"
                + value.userRole + "</td><td>" + value.userStatus
                + "</td></tr>";

        return search_user_result_html;
    }

    // Functions to display AJAX Request results
    function getSuccessMessage(operation) {

        var resultMessage = null;

        switch (operation) {

            case "SAVE":
                resultMessage = "Save operation successful";
                break;
            case "DELETE":
                resultMessage = "Delete operation successful";
                break;

            case "UPDATE":
                resultMessage = "Update operation successful";
                break;

            case "SEARCH":
                resultMessage = "Search successful";
                break;

            case "CONFIG":
                resultMessage = "Configuration successful";
                break;

            case "ACCEPT":
                resultMessage = "Alerts accepted.";
                break;

            case "ASSIGN":
                resultMessage = "Alerts assigned.";
                break;

            case "RESOLVE":
                resultMessage = "Alerts resolved.";
                break;

            default:
                resultMessage = "Operation successfully performed!!";
        } // End switch

        return resultMessage;
    }

    function getFailureMessage(operation) {

        var resultMessage = null;

        switch (operation) {

            case "SAVE":
                resultMessage = "Save operation unsuccessful. Please select a different Id.";
                break;
            case "DELETE":
                resultMessage = "Delete operation unsuccessful. Id does not exist.";
                break;

            case "UPDATE":
                resultMessage = "Update operation unsuccessful. Id does not exist.";
                break;

            case "SEARCH":
                resultMessage = "No records found.";
                break;

            case "CONFIG":
                resultMessage = "Configuration unsuccessful. Id does not exist.";
                break;

            case "ACCEPT":
                resultMessage = "Alerts could not be accepted.";
                break;

            case "ASSIGN":
                resultMessage = "Alerts could not be assigned.";
                break;

            case "RESOLVE":
                resultMessage = "Alerts could not be resolved.";
                break;

            default:
                resultMessage = "Operation unsuccessfull!!";
        }// End switch
        return resultMessage;
    }

    function getDeviceSearchResult(result) {

        $("#search-device-result-div").removeClass("partial-success");
        $("#search-device-result-div").removeClass("error");

        if (result.found == true) {
            var search_device_result_html = "<table summary='DEVICE SEARCH RESULTS'><caption>DEVICE SEARCH RESULTS</caption><thead><tr><th scope='col'>DEVICE ID</th><th scope='col'>NETWORK</th><th scope='col'>STATUS</th><th scope='col'>TYPE</th><th scope='col'>BATTERY</th><th scope='col'>SENSITIVITY</th><th scope='col'>LINK QUALITY</th><th scope='col'>SIGNAL STRENGTH</th></tr></thead><tbody>";
            if (IsSearchById == true) {
                search_device_result_html = populateDeviceSearchResultTable(
                        result.device, search_device_result_html);
            } else {
                $
                        .each(
                                result.devices,
                                function (index, value) {
                                    search_device_result_html = populateDeviceSearchResultTable(
                                            value, search_device_result_html);
                                });
            }
            search_device_result_html += "</tbody></table>";

            $("#search-device-result-div").html(search_device_result_html);
        }

        else {
            $("#search-device-result-div").html("No Device found!!!");
        }
        // Re-setting IsSearchById to false after
        // displaying the results
        IsSearchById = false;

    }

    function getUserSearchResult(result) {

        $("#search-user-result-div").removeClass("partial-success");
        $("#search-user-result-div").removeClass("error");
        console.log("inside user search");
        var search_user_result_html = "<table summary='USER SEARCH RESULTS'><caption>USER SEARCH RESULTS</caption><thead><tr><th scope='col'>USER NAME</th><th scope='col'>FIRST NAME</th><th scope='col'>LAST NAME</th><th scope='col'>ROLE</th><th scope='col'>STATUS</th></tr></thead><tbody>";
        if (IsSearchById == true) {
            search_user_result_html = populateUserSearchResultTable(
                    result.user, search_user_result_html);
        } else {

            $.each(result.users, function (index, value) {
                search_user_result_html = populateUserSearchResultTable(value,
                        search_user_result_html);
            });
        }
        search_user_result_html += "</tbody></table>";

        // $("#search-user-result-div").html(search_user_result_html);
        // Re-setting IsSearchById to false after
        // displaying the results
        IsSearchById = false;
        return search_user_result_html;
    }

    function getMessageSearchResult(result) {

        $("#search-message-result-div").removeClass("partial-success");
        $("#search-message-result-div").removeClass("error");
        var formattedMessageDate;
        var search_message_result_html = "<table summary='MESSAGE SEARCH RESULTS'><caption>MESSAGE SEARCH RESULTS</caption>"
                + "<thead><tr><th scope='col' colspan='4'>DATE/TIME</th><th scope='col'>MESSAGE ID</th><th scope='col'>DEVICE ID</th>"
                + "<th scope='col'>TYPE</th><th scope='col'>DEVICE TYPE</th><th scope='col' colspan='2'>MESSAGE</th></tr></thead>"
                + "<tfoot><tr><td colspan='10'>End of message search results</td></tr></tfoot><tbody>";

        $.each(result.messages, function (index, value) {
            formattedMessageDate = new Date(value.messageDate);
            search_message_result_html += "<tr><td colspan='4'>"
                    + formattedMessageDate + "</td><td>" + value.messageId
                    + "</td><td>" + value.messageDeviceId + "</td><td>"
                    + value.messageType + "</td><td>" + value.messageDeviceType
                    + "</td><td colspan='2'>" + value.messageContent
                    + "</td></tr>";
        });
        search_message_result_html += "</tbody></table>";
        $("#search-message-result-div").html(search_message_result_html);
    }
    /*
     * Function to display the results of CRUD Ajax requests
     */
    function displayRequestResult(response, status, resultDiv, operation) {

        $(resultDiv).html("");
        var displayMessage = "";

        if (resultDiv == "#search-user-result-div" && response.found == true) {
            displayMessage = getUserSearchResult(response);
        } else if (resultDiv == "#search-device-result-div"
                && response.found == true) {
            displayMessage = getDeviceSearchResult(response);
        } else if (resultDiv == "#search-message-result-div"
                && response.found == true) {
            displayMessage = getMessageSearchResult(response);
        } else if (status == "success" && response == "success") {
            $(resultDiv).removeClass("partial-success");
            $(resultDiv).addClass("success");
            displayMessage = getSuccessMessage(operation);
        } else if (status == "success"
                && (response == "failure" || response.found == false)) {
            $(resultDiv).removeClass("success");
            $(resultDiv).addClass("partial-success");
            displayMessage = getFailureMessage(operation);
        } else if (status = "error") {
            displayMessage = "Error Executing AJAX Request: " + response.error;
        }
        $(resultDiv).html(displayMessage);
    }
    // End of private methods and properties

    // public methods and properties
    return {
        setUserDropdown: function (dropdown) {
            setUserDropdownData(dropdown);
        },
        setNetworkDropdown: function () {
            populateNetworkDropdown();
        },
        setVideoCameraDropdown: function () {
            populateVideoCameraDropdown();
        },
        setFieldUserDropdown: function () {
            setFieldUserDropdownData();
        },
        setAssignedAlertDropdown: function () {
            setAssignedAlertDropdownData();
        },
        setBindings: function () {
            /*
             * Setting a trigger on device list drop down so when a device id is
             * seleceted from the drop down, the map pans to the marker of that
             * device and info popup is opened on that marker
             */
            $('#deviceListDropdown').change(function () {

                var deviceMap = $("#map-div").gmap3("get");
                var dropdownDeviceId = $("#deviceListDropdown").val();

                var deviceMarker = $("#map-div").gmap3({
                    get: {
                        id: dropdownDeviceId
                    }
                });

                // Pan to the marker for the device id clicked from the device
                // drop down list
                deviceMap.panTo(deviceMarker.getPosition());
                // open the info window over the marker
                google.maps.event.trigger(deviceMarker, 'click');
            });

            /*
             * Setting a trigger on user update drop down so when a user id is
             * seleceted from the drop down, the user update form is
             * automatically populated with the user data
             */
            $('#updateUserId')
                    .change(
                            function () {

                                var searchUserId = $("#updateUserId").val();

                                if (searchUserId == "" || searchUserId == " ") {
                                    $("#update-user-result-div").addClass(
                                            "error");
                                    $("#update-user-result-div")
                                            .html(
                                                    "Please select a user id from the dropdown to update!!!");
                                } else {
                                    var requestUrl = "/cnc/user/searchById";
                                    var sendData = {
                                        userIdSearchParam: searchUserId
                                    };
                                    cncDisplay
                                            .ExecuteAjaxRequest(
                                                    requestUrl,
                                                    sendData,
                                                    requestTypeGet,
                                                    receiveTypeJson,
                                                    function (response, status,
                                                            error) {
                                                        populateUserUpdateForm(response);
                                                    });
                                }
                            });

            /*
             * Setting a trigger on video camera list drop down so when a video
             * camera id is seleceted from the drop down, the video stream from
             * that video camera is displayed inside the video player
             */
            $('#videoCameraDropdown').change(function () {
                var videoStreamLink = $('#videoCameraDropdown').val();
                var video = document.getElementById("tab-video-player");
                video.src = videoStreamLink;
                video.load();
            });

            // End of dropdown change bindings
            // Start of button bindings

            $("#searchMessagesByTypeAndDateButton")
                    .click(
                            function (event) {

                                var resultDiv = "#search-message-result-div";
                                if ($("#startDate").val() == ""
                                        || $("#endDate").val() == "") {
                                    displayEmptyFieldMessage(resultDiv,
                                            textfield);
                                } else {
                                    var requestUrl = "/cnc/message/searchByTypeAndDate";
                                    var sendData = $("#messageSearchByDateForm")
                                            .serialize();
                                    cncDisplay.ExecuteAjaxRequest(requestUrl,
                                            sendData, requestTypeGet,
                                            receiveTypeJson, function (response,
                                                    status, error) {
                                                displayRequestResult(response,
                                                        status, resultDiv,
                                                        searchOperation);
                                            });
                                }

                            });

            $("#searchMessagesByDeviceIdButton")
                    .click(
                            function (event) {

                                var resultDiv = "#search-message-result-div";
                                searchMessageDeviceId = $(
                                        "#messageSearchByIdTextField").val();
                                if (searchMessageDeviceId == "") {
                                    displayEmptyFieldMessage(resultDiv,
                                            textfield);
                                } else {

                                    var requestUrl = "/cnc/message/searchByDeviceId";
                                    var sendData = {
                                        messageDeviceIdSearchParam: searchMessageDeviceId
                                    };
                                    cncDisplay.ExecuteAjaxRequest(requestUrl,
                                            sendData, requestTypeGet,
                                            receiveTypeJson, function (response,
                                                    status, error) {
                                                displayRequestResult(response,
                                                        status, resultDiv,
                                                        searchOperation);
                                            });
                                }
                            });

            $("#searchDeviceByIdButton").click(
                    function (event) {

                        var searchDeviceId = $("#deviceIdSearchTextField")
                                .val();
                        var resultDiv = "#search-device-result-div";
                        if (searchDeviceId == "" || searchDeviceId == " ") {
                            displayEmptyFieldMessage(resultDiv, textfield);
                        } else {
                            IsSearchById = true;
                            var requestUrl = "/cnc/device/searchById";
                            var sendData = {
                                deviceIdSearchParam: searchDeviceId
                            };
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypeGet, receiveTypeJson, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, searchOperation);
                                    });
                        }
                    });

            $("#searchDevicesByNetworkButton").click(
                    function (event) {

                        var searchDeviceNetwork = $(
                                "#deviceNetworkSearchDropdown").val();
                        var resultDiv = "#search-device-result-div";

                        if (searchDeviceNetwork == "") {
                            displayEmptyFieldMessage(resultDiv, dropdown);
                        } else {

                            var requestUrl = "/cnc/device/searchByNetworkId";
                            var sendData = {
                                networkIdSearchParam: searchDeviceNetwork
                            };
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypeGet, receiveTypeJson, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, searchOperation);
                                    });
                        }
                    });

            $("#searchDevicesByTypeButton").click(
                    function (event) {

                        var searchDeviceType = $("#deviceTypeSearchDropdown")
                                .val();

                        var resultDiv = "#search-device-result-div";
                        var requestUrl = "/cnc/device/searchByType";
                        var sendData = {
                            deviceTypeSearchParam: searchDeviceType
                        };
                        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                requestTypeGet, receiveTypeJson, function (
                                        response, status, error) {
                                    displayRequestResult(response, status,
                                            resultDiv, searchOperation);
                                });
                    });

            $("#searchDevicesByStatusButton").click(
                    function (event) {

                        var searchDeviceStatus = $(
                                "#deviceStatusSearchDropdown").val();
                        var resultDiv = "#search-device-result-div";
                        var requestUrl = "/cnc/device/searchByStatus";
                        var sendData = {
                            deviceStatusSearchParam: searchDeviceStatus
                        };
                        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                requestTypeGet, receiveTypeJson, function (
                                        response, status, error) {
                                    displayRequestResult(response, status,
                                            resultDiv, searchOperation);
                                });
                    });

            $("#deleteDeviceButton").click(
                    function (event) {

                        var resultDiv = "#delete-device-result-div";
                        var deleteDeviceId = $("#deviceIdDeleteTextField")
                                .val();

                        if (deleteDeviceId == "" || deleteDeviceId == " ") {
                            displayEmptyFieldMessage(resultDiv, textfield);
                        } else {
                            var requestUrl = "/cnc/device/delete";
                            var sendData = {
                                deviceIdDeleteParam: deleteDeviceId
                            };
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypeGet, receiveTypeText, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, deleteOperation);
                                    });
                        }
                    });

            $("#configDeviceButton").click(
                    function (event) {

                        var resultDiv = "#config-device-result-div";
                        var configDeviceId = $("#deviceIdConfigTextField")
                                .val();
                        if (configDeviceId == "" || configDeviceId == " ") {
                            displayEmptyFieldMessage(resultDiv, textfield);
                        } else {

                            var requestUrl = "/cnc/device/searchById";
                            var sendData = {
                                deviceIdSearchParam: configDeviceId
                            };
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypeGet, receiveTypeText, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, configOperation);
                                    });

                        }
                    });

            $("#saveNetworkButton").click(
                    function (event) {

                        var resultDiv = "#create-network-result-div";
                        if ($("#networkId").val() == ""
                                || $("#networkName").val() == "") {
                            displayEmptyFieldMessage(resultDiv, textfield);

                        } else {
                            var requestUrl = "/cnc/network/create";
                            var sendData = $("#networkAddForm").serialize();
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypePost, receiveTypeText, 
                                    function (response, status, error) {
                                        displayRequestResult(response, status, resultDiv, saveOperation);
                                    });
                        }

                    });

            $("#saveUserButton").click(
                    function (event) {

                        var resultDiv = "#create-user-result-div";

                        if ($("#userId").val() == ""
                                || $("#firstName").val() == ""
                                || $("#lastName").val() == ""
                                || $("#password").val() == "") {
                            displayEmptyFieldMessage(resultDiv, textfield);

                        } else {
                            var requestUrl = "/cnc/user/create";
                            var sendData = $("#userAddForm").serialize();
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypePost, receiveTypeText, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, saveOperation);
                                    });

                        }
                    });

            $("#searchUserByIdButton").click(
                    function (event) {

                        var searchUserId = $("#userIdSearchTextField").val();
                        var resultDiv = "#search-user-result-div";

                        if (searchUserId == "" || searchUserId == " ") {
                            displayEmptyFieldMessage(resultDiv, textfield);
                        } else {
                            IsSearchById = true;
                            var searchData = $("#userIdSearchTextField").val();
                            var requestUrl = "/cnc/user/searchById";
                            var sendData = {
                                userIdSearchParam: searchData
                            };
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypeGet, receiveTypeJson, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, searchOperation);
                                    });
                        }
                    });

            $("#searchUsersByRoleButton").click(
                    function (event) {

                        var resultDiv = "#search-user-result-div";
                        var searchData = $("#userRoleSearchDropdown").val();
                        var requestUrl = "/cnc/user/searchByRole";
                        var sendData = {
                            userRoleSearchParam: searchData
                        };
                        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                requestTypeGet, receiveTypeJson, function (
                                        response, status, error) {
                                    displayRequestResult(response, status,
                                            resultDiv, searchOperation);
                                });
                    });

            $("#searchUsersByNameButton").click(
                    function (event) {

                        var resultDiv = "#search-user-result-div";
                        var searchData = $("#userNameSearchTextField").val();
                        if (searchData == "") {
                            displayEmptyFieldMessage(resultDiv, textfield);
                        } else {
                            var requestUrl = "/cnc/user/searchByName";
                            var sendData = {
                                userNameSearchParam: searchData
                            };
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypeGet, receiveTypeJson, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, searchOperation);
                                    });
                        }
                    });

            $("#searchUsersByStatusButton").click(
                    function (event) {

                        var resultDiv = "#search-user-result-div";
                        var searchData = $("#userStatusSearchDropdown").val();
                        var requestUrl = "/cnc/user/searchByStatus";
                        var sendData = {
                            userStatusSearchParam: searchData
                        };
                        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                requestTypeGet, receiveTypeJson, function (
                                        response, status, error) {
                                    displayRequestResult(response, status,
                                            resultDiv, searchOperation);
                                });
                    });

            $("#updateUserButton").click(
                    function (event) {

                        var resultDiv = "#update-user-result-div";
                        var updateUserIdValue = $("#updateUserId").val();
                        if (updateUserIdValue == "") {
                            displayEmptyFieldMessage(resultDiv, dropdown);
                        } else {
                            var requestUrl = "/cnc/user/update";
                            var sendData = $("#updateUserForm").serialize();
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypePost, receiveTypeText, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, updateOperation);
                                    });
                        }
                    });

            $("#deleteUserButton").click(
                    function (event) {

                        var resultDiv = "#delete-user-result-div";

                        var deleteUserId = $("#deleteUserIdDropdown").val();
                        if (deleteUserId == "" || deleteUserId == " ") {
                            displayEmptyFieldMessage(resultDiv, dropdown);
                        } else {
                            var requestUrl = "/cnc/user/delete";
                            var sendData = {
                                userIdDeleteParam: deleteUserId
                            };
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypeGet, receiveTypeText, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, deleteOperation);
                                    });
                        }
                    });

            $("#acceptAlertButton").click(
                    function (event) {

                        var resultDiv = "#alert-window-result-div";
                        var deviceId = $("#receivedAlertDeviceId").val();
                        $('input:hidden[id="assignedBy"]').val(
                                cncDisplay.getUsername());
                        if (deviceId == null || deviceId.length == 0) {
                            displayEmptyFieldMessage(resultDiv, dropdown);
                        } else {
                            var requestUrl = "/cnc/alert/acceptAlert";
                            var sendData = $("#alertForm").serialize();
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypePost, receiveTypeText, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, acceptOperation);
                                    });

                            $("#receivedAlertDeviceId option").each(function () {
                                if ($(this).val() == deviceId)
                                    $(this).css("background-color", "orange");
                            });
                        }

                    });

            $("#assignAlertButton").click(
                    function (event) {

                        var resultDiv = "#alert-window-result-div";
                        var deviceId = $("#receivedAlertDeviceId").val();
                        var assignedTo = $("#assignedTo").val();
                        $('input:hidden[id="assignedBy"]').val(
                                cncDisplay.getUsername());

                        if (deviceId == "" || assignedTo == "") {
                            displayEmptyFieldMessage(resultDiv, dropdown);
                        } else {
                            var requestUrl = "/cnc/alert/assignAlert";
                            var sendData = $("#alertForm").serialize();
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypePost, receiveTypeText, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, assignOperation);
                                    });

                            $("#receivedAlertDeviceId option").each(function () {
                                if ($(this).val() == deviceId) {
                                    $(this).remove();
                                }
                            });
                        }
                    });

            $("#resolveAlertButton").click(
                    function (event) {

                        var resultDiv = "#alert-resolve-result-div";
                        var deviceId = $("#assignedAlertDeviceId").val();
                        $('input:hidden[id="resolvedBy"]').val(
                                cncDisplay.getUsername());

                        if (deviceId == "") {
                            displayEmptyFieldMessage(resultDiv, dropdown);
                        } else {
                            var requestUrl = "/cnc/alert/resolveAlert";
                            var sendData = $("#alertResolvedForm").serialize();
                            cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                                    requestTypePost, receiveTypeText, function (
                                            response, status, error) {
                                        displayRequestResult(response, status,
                                                resultDiv, resolveOperation);
                                    });

                            cncDisplay.cleanupResolvedAlert(deviceId);

                        }
                    });
        }
    }
    // End of public methods and properties

}();