/*
 * Main JavaScript file for C2I display. This JavaScript file is designed
 * using the Singleton design pattern with public and private properties
 * and methods. The public properties and methods can be accessed from anywhere
 * using the cncDisplay variable
 */

//Singleton
var cncDisplay = function () {

    // Private properties and methods
    var userRole = null;
    var username = null;
    var alertCounter = 0;
    var noOfAlertsPending = 0;
    var alertInfowindow = null;
    var messageType = "";
    var $blinker = null;
    var ws = null;
    var statusMessagePopupTimer = null;
    /*
     * Variable to determine if search is done by Id to determine the result as
     * a singular value. For searches performed through other filters, there
     * could be multiple results.
     */
    var IsSearchById = false;
    var map;
    var markerArray = [];

    var imageDirectoryLocation = "http://" + window.location.host + "/cnc/resources/images/";
    var videoDirectoryLocation = "http://" + window.location.host + "/cnc/resources/videos/";
    var audioDirectoryLocation = "http://" + window.location.host + "/cnc/resources/audio/";
    var javascriptDirectoryLocation = "http://" + window.location.host + "/cnc/resources/js/";
    var batteryInfoPopupText = "<tr><td height='10' width='10'><img src='" + imageDirectoryLocation
            + "battery_icon.png' height='10' width='20' align='middle'></img></td><td height='10' width='250'> <b>Battery Level:</b> ";
    var linkQualityInfoPopupText = "<tr height='10' width='10'><td><img src='" + imageDirectoryLocation
            + "link_quality_icon.png' height='10' width='20' align='middle'></img></td><td height='10' width='250'> <b>Link Quality (LQI):</b> ";
    var sensitivityInfoPopupText = "<tr><td height='10' width='10'><img src='"
            + imageDirectoryLocation
            + "sensitivity_icon.png' height='10' width='20' align='middle'></img></td><td height='10' width='250'> <b> IR Sensitivity:</b> ";
    var deviceIdInfoPopupText = "<tr><td height='10' width='10'><img src='"
            + imageDirectoryLocation
            + "device_id_icon.png' height='10' width='20' align='middle'></img></td><td height='10' width='250'> <b>Device Id:</b> ";
    var networkIdInfoPopupText = "<tr><td height='10' width='10'><img src='"
            + imageDirectoryLocation
            + "network_id_icon.png' height='10' width='20' align='middle'></img></td><td height='10' width='250'> <b>Network Id:</b> ";
    var locationInfoPopupText = "<tr><td height='10' width='10'><img src='"
            + imageDirectoryLocation
            + "location_icon.png' height='10' width='20' align='middle'></img></td><td height='10' width='250'> <b>Location:</b> ";
    var signalStrengthInfoPopupText = "<tr><td height='10' width='10'><img src='"
            + imageDirectoryLocation
            + "signal_strength_icon.png' height='10' width='20' align='middle'></img></td><td height='10' width='250'> <b>Signal Strength (RSSI):</b> ";
    var deviceStatusInfoPopupText = "<tr><td height='10' width='10'><img src='"
            + imageDirectoryLocation
            + "status_green_icon.png' height='10' width='20' align='middle'></img></td><td height='10' width='250'> <b> Status:</b> ";
    var videoHtml = "<div><video id='infowindow-video-player' width='320' height='240' controls autoplay><source src=''/><p class='warning'>Your browser does not support HTML5 video.</p></video></div>";

    var animatedSensorIcon = imageDirectoryLocation + "sensor_icon_animated.gif";
    var staticSensorIcon = imageDirectoryLocation + "sensor_icon_static.png";

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



    /*********************************************************************************
     * Icons to display on Map
     *********************************************************************************/
    var c2iSystemMarker = imageDirectoryLocation + "mac_icon.png";
    var sensorStatusOffMarker = imageDirectoryLocation + "frame_green.v7.icon.off.status.gif";
    var sensorStatusOnMarker = imageDirectoryLocation + "frame_green0.gif";

    var sensorAnimatedMarker = imageDirectoryLocation + "frame_green.v7.gif";
    var routerAnimatedMarker = imageDirectoryLocation + "gateway_icon_animated.gif";

    var gatewayMarker = imageDirectoryLocation + "gateway_icon_static.png";
    var videoCameraMarker = imageDirectoryLocation + "video_camera_icon_static.png";
    var humanAttachedMarker = imageDirectoryLocation + "red_dot_animated.gif";

    var explosiveMineOnMarker = imageDirectoryLocation + "ball_red.gif";
    /*********************************************************************************/

    /*
     * Function to initialize the main display tabs and side tabs
     */
    function setTabs() {
        // Initializing the main display tabs
        $("#main-display-tabs").tabs({
            beforeActivate: function (event, ui) {
                if (ui.newPanel.selector == "#video-tab") {
                    $("#videoCameraDropdown").html("");
                    cncEvents.setVideoCameraDropdown();
                } else if (ui.newPanel.selector == "#map-tab") {
                    google.maps.event.trigger(map, 'resize');
                } else if (ui.newPanel.selector == "#console-tab") {
                    $blinker.blinker('stop');
                    if (userRole == "ONSITE_USER" || userRole == "COMMANDER") {
                        $("#assignedTo").html("");
                        cncEvents.setFieldUserDropdown();
                    } else if (userRole == "FIELD_USER") {
                        $("#assignedAlertDeviceId").html("");
                        cncEvents.setAssignedAlertDropdown();
                    }
                }
            }
        });
        // Initializing the left side vertical device config tabs
        $("#device-side-tabs").tabs().addClass(
                "ui-tabs-vertical ui-helper-clearfix");
        $("#device-side-tabs li").removeClass("ui-corner-top").addClass(
                "ui-corner-left");
        $("#device-side-tabs").tabs({
            beforeActivate: function (event, ui) {
                if (ui.newPanel.selector == "#search-device-tab") {
                    $("#deviceNetworkSearchDropdown").html("");
                }
                cncEvents.setNetworkDropdown();
            }
        });
        // Initializing the left side vertical alerts tabs
        $("#alerts-side-tabs").tabs().addClass("ui-tabs-vertical ui-helper-clearfix");
        $("#alerts-side-tabs li").removeClass("ui-corner-top").addClass("ui-corner-left");

        // Initializing the left side vertical user tabs
        $("#user-side-tabs").tabs().addClass(
                "ui-tabs-vertical ui-helper-clearfix");
        $("#user-side_-abs li").removeClass("ui-corner-top").addClass(
                "ui-corner-left");
        $("#user-side-tabs").tabs({
            beforeActivate: function (event, ui) {
                if (ui.newPanel.selector == "#update-user-tab") {
                    $("#updateUserId").html("");
                    cncEvents.setUserDropdown("update");
                } else if (ui.newPanel.selector == "#delete-user-tab") {
                    $("#deleteUserIdDropdown").html("");
                    cncEvents.setUserDropdown("delete");
                }
            }
        });

        // Setting the alert blinker so that when an alert is received, the
        // alert
        // tab starts blinking
        $blinker = $(".blinker").blinker({
            css_1: {
                color: 'white',
                backgroundColor: '#F00'
            },
            cycle: 1000,
            ratio: 0.5
        });

    }

    /*
     * Function for setting permissions to users based on role
     *
     * COMMANDER - Permissions to the entire C2I ADMIN - Permissions to Map,
     * Console, User management, Device configuration, Message Search, Video
     * Streaming and Logs ONSITE_USER - Permissions to Map, Console, Alert
     * acceptance and assignment, Message Search, Logs, Device Search and Video
     * Streaming FIELD_USER - Permissions to Map, Console, Assigned Alerts,
     * Message Search, Device Search and Video Streaming
     */

    function setUserPermissions() {
        if (userRole == 'ONSITE_USER') {
            // Disable the add network, delete device and
            // config device tabs under the device
            // configuration tab
            $("#device-side-tabs").tabs("option", "disabled", [0, 2, 3]);
            // Set the active tab in device configuration to
            // device search.
            $("#device-side-tabs").tabs("option", "active", [1]);
            // Disable the user management tab
            $("#main-display-tabs").tabs("option", "disabled", [4]);
            // Disable the alerts resolution tab
            $("#alerts-side-tabs").tabs("option", "disabled", [1]);

        } else if (userRole == 'FIELD_USER') {
            // Disable the add network, delete device and
            // config device tabs under the device
            // configuration tab
            $("#device-side-tabs").tabs("option", "disabled", [0, 2, 3]);
            // Set the active tab in device configuration to
            // device search.
            $("#device-side-tabs").tabs("option", "active", [1]);
            // Disable the assigned alerts tab since alerts
            // can only be assigned to Field Users
            $("#alerts-side-tabs").tabs("option", "disabled", [0]);
            // Set the active tab for alerts to assigned
            // alerts tab for field users
            $("#alerts-side-tabs").tabs("option", "active", 1);
            // Disable the User management and Logs tabs
            $("#main-display-tabs").tabs("option", "disabled", [4, 7]);
        }

        else if (userRole == 'ADMIN') {
            // Disable the Alerts tab since an admin cannot
            // accept, assign or resolve alerts
            $("#main-display-tabs").tabs("option", "disabled", [2]);
        }

        else if (userRole == 'BATTALION_COMMANDER'
                || userRole == 'BATTALION_SUPPORT') {
            // Disable the add network, delete device and
            // config device tabs under the device
            // configuration tab
            $("#device-side-tabs").tabs("option", "disabled", [0, 2, 3]);
            // Set the active tab in device configuration to
            // device search.
            $("#device-side-tabs").tabs("option", "active", [1]);
            // Disable the alerts and user management tabs
            $("#main-display-tabs").tabs("option", "disabled", [2, 4]);
        }

    }

    /*
     * Function to initialize Sock JS STOMP messaging protocol for creating and
     * subscribing to a RabbitMQ queue for this particular client so that the
     * messages sent by the C2I server can be received inside the browser and
     * displayed on the messaging console
     */
    function initStompMessaging() {

        // Creating a STOMP connection with the C2I application server
        ws = new SockJS('http://' + window.location.hostname + ':15674/stomp');

        // var ws = new
        // SockJS('http://queue-c2i.ugs.sensorflock.com:15674/stomp');

        // Upgrading the protocol from http to STOMP
        var client = Stomp.over(ws);
        client.heartbeat.outgoing = 0;
        client.heartbeat.incoming = 0;
        client.debug = displayMessage('#log-console-div');
        var print_console = displayMessage('#main-console-div');
        var on_connect = function (x) {
            // Subscribing to the auto-delete topic type queue with routing key:
            // browser-clients
            try {
                id = client.subscribe("/topic/browser-clients", function (message) {
                    var processedMessage = processMessage(message);
                    print_console(processedMessage, "to_console");
                });
            } catch (exception) {
                console.log("Error subscribing to queue" + exception);
                alert('Connection is not established. Try again');
            }
        };
        var on_error = function (err) {
            console.log('Error: STOMP connection failed');
            client.debug = displayMessage('#log-console-div');
            //alert(displayMessage('#log-console-div'));
            alert('Connection was closed. Application is going to be refreshed automatically to establish connection again.');
            window.location.reload();
        };
        // Connecting the browser client with login: guest and passwod: guest
        client.connect('guest', 'guest', on_connect, on_error, '/');
    }



    function setMapLeaflet() {
        var centerLat;
        var centerLong;

        $.ajax({
            type: "get",
            url: "/cnc/device/getAllDevices",
            dataType: "json",
            async: false,
            success: function (data, status) {
                result = data;
            },
            error: function (data, status, er) {
                console.log("Error: Devices could not be mapped!!!");
            }
        });

        // Now get Center point to make center of map 
        $.each(result.devices, function (index, value) {

            if (value.deviceLocation != null) {

                var deviceIdCenter = value.deviceId;
                if (deviceIdCenter == 0) {
                    centerLat = value.deviceLocation.latitude;
                    centerLong = value.deviceLocation.longitude
                }

            }
        });

        //var cloudmadeUrl = 'http://192.168.66.150:8083/leaflet/tiles/{styleId}/{z}/{x}/{y}.png', cloudmadeAttribution = 'R&E Lab C&IT Br GHQ Rwp - Project UGS';
        //var cloudmadeUrl = 'http://localhost:8083/leaflet/tiles/{styleId}/{z}/{x}/{y}.png', cloudmadeAttribution = 'R&E Lab Military College of Signals';
        //var cloudmadeUrl = 'http://' + window.location.hostname + ':' + window.location.port + '/leaflet/tiles/{styleId}/{z}/{x}/{y}.png', cloudmadeAttribution = 'R&E Lab Military College of Signals';
        var cloudmadeUrl = 'http://' + window.location.hostname + ':' + window.location.port + '/cnc/resources/{styleId}/{z}/{x}/{y}.png', cloudmadeAttribution = 'R&E Lab Military College of Signals';

        var satelite = L.tileLayer(cloudmadeUrl, {
            styleId: 'satelite',
            attribution: cloudmadeAttribution
        });
        var terrain = L.tileLayer(cloudmadeUrl, {
            styleId: 'terrain',
            attribution: cloudmadeAttribution
        });
        // terrain = L.tileLayer(cloudmadeUrl, {styleId: 'terrain', attribution:
        // cloudmadeAttribution});

        // For Multipe Popups open at the same time 
        L.Map = L.Map.extend({
            openPopup: function (popup) {
                // this.closePopup();  // just comment this
                this._popup = popup;
                return this.addLayer(popup).fire('popupopen', {popup: this._popup});
            }
        });

        map = L.map('map', {
            //center : new L.LatLng(33.586018, 73.047219),
            center: new L.LatLng(centerLat, centerLong),
            zoom: 17,
            layers: [satelite]
        });

        map.options.maxZoom = 19;
        map.options.minZoom = 2;

        var baseMaps = {
            "Satelite": satelite,
            "Terrain": terrain
        };


        L.control.layers(baseMaps, null, {position: 'topleft'}).addTo(map);

        // L.tileLayer('http://{s}.tile.cloudmade.com/API-key/997/256/{z}/{x}/{y}.png',
        // {
        // attribution: 'Map data &copy; <a
        // href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a
        // href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>,
        // Imagery © <a href="http://cloudmade.com">CloudMade</a>',
        // maxZoom: 18
        // }).addTo(map);

        // map.fitBounds([
        // [32.39924, 70.017478],
        // [32.427391, 70.06151]
        // ]);

        // Getting device information for all the devices stored in the database
        // to plot the device data on the map

        $.each(result.devices, function (index, value) {

            if (value.deviceLocation != null) {

                var deviceId = value.deviceId;
                var deviceType = value.deviceType;
                var deviceStatus = value.deviceStatus;

                var deviceMarkerLocation = [];

                $("#deviceListDropdown2").append($("<option>", {
                    text: value.deviceId + " -- " + getNodeStringByDeviceType(value.deviceType),
                    value: value.deviceId
                }));

                try {
                    deviceMarkerLocation.push(value.deviceLocation.latitude);
                    deviceMarkerLocation.push(value.deviceLocation.longitude);
                } catch (error) {
                    console.log("Error retrieving device location:" + error);
                }

                createAndPlotMarkerForLeaflet(deviceId, deviceType,
                        deviceMarkerLocation, deviceStatus);
            }
        });

        /***********************************************************************
         * When user selects any device from dropdown then go to selected Marker
         * and display the marker information
         **********************************************************************/
        $("#deviceListDropdown2")
                .change(
                        function () {
                            var deviceId = $("#deviceListDropdown2").val();

                            var deviceData = cncDisplay.getDeviceById(deviceId);
                            var InfowindowData = generatePopupHtmlForLeaflet(deviceData.device);

                            var alertMarker;
                            for (var index in markerArray) {
                                var keyValue = markerArray[index];

                                if (keyValue[0] == deviceId) {
                                    alertMarker = keyValue[1];
                                }

                            }
                            var popupText = L.popup().setContent(InfowindowData);// .openOn(alertMarker);
                            alertMarker.bindPopup(popupText).openPopup();

                        });

        // Setting the Animated Image below the Dropdown
        $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "blinking_radar.modified.gif) no-repeat");
        $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");
    }



    /*
     * ****************************** FOR LEAFLET ***************************
     * Function to create markers from the device information supplied. This
     * function also creates info popup html text and sets up trigger functions
     * on the markers. Also, marker icons are created and plotted on the map by
     * this function.
     */
    function createAndPlotMarkerForLeaflet(deviceId, deviceType,
            deviceMarkerLocation, deviceStatus) {

        var deviceMarkerIcon;
        var marker;
        var circle;

        try {
            deviceMarkerIcon = generateDeviceMarkerIconForLeaflet(deviceType, deviceStatus);

        } catch (error) {
            console.log("Error creating device marker icon:" + error);
        }

        var infowindowId = deviceId + "-infowindow";
        var circleId = deviceId + "-circle";

        if (deviceType == "C2I_SYSTEM" ||
                deviceType == "PIR_SENSOR_NODE" ||
                deviceType == "DUAL_SENSOR_NODE" ||
                deviceType == "SEISMIC_SENSOR_NODE" ||
                deviceType == "HUMAN_ATTACHED" ||
                deviceType == "VIDEO_CAMERA_NODE" ||
                deviceType == "ROUTER_NODE" ||
                deviceType == "GATEWAY_NODE" ||
                deviceType == "EXPLOSIVE_MINE") {

            var deviceTypeChar = '';
            if (deviceType == "PIR_SENSOR_NODE") {
                deviceTypeChar = 'P';
            }
            else if (deviceType == "DUAL_SENSOR_NODE") {
                deviceTypeChar = 'D';
            }
            else if (deviceType == "SEISMIC_SENSOR_NODE") {
                deviceTypeChar = 'S';
            }
            else if (deviceType == "C2I_SYSTEM") {
                deviceTypeChar = 'C2I';
            }
            else if (deviceType == "ROUTER_NODE") {
                deviceTypeChar = 'R';
            }
            else if (deviceType == "EXPLOSIVE_MINE") {
                deviceTypeChar = 'E';
            }


            circle = L.circle(deviceMarkerLocation, 20, {
                color: 'red',
                fillColor: '#f03',
                fillOpacity: 0.3
            });

            marker = new L.Marker(deviceMarkerLocation, {
                draggable: false,
                icon: deviceMarkerIcon,
                id: deviceId,
                title: deviceId

            });
            //marker.bindLabel(deviceId + " (" + deviceTypeChar + ")" , {noHide : true});
            marker.bindLabel(deviceId, {noHide: true});


            map.addLayer(marker);

            if (deviceType != "EXPLOSIVE_MINE") {
                var deviceData = cncDisplay.getDeviceById(deviceId);
                var InfowindowData = generatePopupHtmlForLeaflet(deviceData.device);

                marker.bindPopup(InfowindowData);// .openPopup(); // If you want
                // to display all the markers
                // when Page load.
            }
            else {
                var deviceData = cncDisplay.getDeviceById(deviceId);
                var InfowindowData = generatePopupExplosiveMineLeaflet(deviceData.device);
                marker.bindPopup(InfowindowData);
            }


            var objKeyValue = [deviceId, marker];
            markerArray.push(objKeyValue);

        }

    }


    /*
     * Function to generate a marker icon depending on the type of device for
     * Leaflet Map
     */
    function generateDeviceMarkerIconForLeaflet(deviceType, deviceStatus) {
        var greenIcon;

        var deviceMarkerIcon;

        // For C2I System
        if (deviceType == "C2I_SYSTEM") {
            greenIcon = L.icon({
                iconUrl: c2iSystemMarker,
                iconSize: [30, 30]
            });
        }
        // For Router
        else if (deviceType == "ROUTER_NODE") {
            greenIcon = L.icon({
                iconUrl: routerAnimatedMarker,
                iconSize: [50, 50]
            });
        }
        // For Sensors
        else if (deviceType == "PIR_SENSOR_NODE" ||
                deviceType == "DUAL_SENSOR_NODE" ||
                deviceType == "SEISMIC_SENSOR_NODE") {

            // If Device Status is OFF (If to display different ICONs)
            if (deviceStatus == "OFF") {
                greenIcon = L.icon({
                    iconUrl: sensorStatusOffMarker,
                    iconSize: [20, 20]

                });
            }
            // Else Device status is ON or SLEEP
            else {
                greenIcon = L.icon({
                    iconUrl: sensorStatusOnMarker,
                    iconSize: [50, 50]
                });
            }

        }
        else if (deviceType == "GATEWAY_NODE") {
            greenIcon = L.icon({
                iconUrl: gatewayMarker
            });
        }
        else if (deviceType == "VIDEO_CAMERA_NODE") {
            greenIcon = L.icon({
                iconUrl: videoCameraMarker,
                iconSize: [40, 40]
            });
        }
        else if (deviceType == "HUMAN_ATTACHED") {
            greenIcon = L.icon({
                iconUrl: humanAttachedMarker
            });
        }
        else if (deviceType == "EXPLOSIVE_MINE") {
            greenIcon = L.icon({
                iconUrl: explosiveMineOnMarker,
                iconSize: [20, 20]
            });
        }
        return greenIcon;
    }


    /**
     * Popup for Leaflet
     */
    function generatePopupHtmlForLeaflet(deviceValue) {
        if (deviceValue != null) {
            var batteryStatus = "Down";

            if (deviceValue.deviceSignalStrength == null
                    || deviceValue.deviceSignalStrength == "") {
                deviceValue.deviceSignalStrength = "N/A";
            } else {
                deviceValue.deviceSignalStrength = deviceValue.deviceSignalStrength
                        + " dBm";
            }
            if (deviceValue.deviceSensitivity == null
                    || deviceValue.deviceSensitivity == "") {
                deviceValue.deviceSensitivity = "N/A";
            }
            if (deviceValue.deviceBattery == null
                    || deviceValue.deviceBattery == "") {
                deviceValue.deviceBattery = "N/A";
            }
            if (deviceValue.deviceLinkQualityIndicator == null
                    || deviceValue.deviceLinkQualityIndicator == "") {
                deviceValue.deviceLinkQualityIndicator = "N/A";
            }

            var deviceTypeStr;
            deviceTypeStr = getNodeStringByDeviceType(deviceValue.deviceType);

            // Battery Status 
            if (deviceValue.deviceBattery == 3) {
                batteryStatus = "High";
            }
            else if (deviceValue.deviceBattery == 2) {
                batteryStatus = "Medium";
            }
            else if (deviceValue.deviceBattery == 1) {
                batteryStatus = "Low";
            }
            else {
                batteryStatus = "Down";
            }


            var devicePopupHtml = "<div class='infowindow FL wow flipInY animated' data-wow-delay='1300ms'><font size=2><table><tr><td colspan='2' height='10' width='10'><center><b>"
                    + deviceTypeStr
                    + "</b></center></td></tr>"

                    + "<tr><td height='10' width='10'>Device_Id</td><td height='10' width='250'  align='center'>  "
                    + deviceValue.deviceId
                    + "</td></tr>"

                    + "<tr><td>Network_Id</td><td height='10' width='250'  align='center'>"
                    + deviceValue.networkId
                    + "</td></tr>"

                    + "<tr><td height='10' width='10'>Status</td><td height='10' width='250' align='center'> "
                    + deviceValue.deviceStatus
                    + "</td></tr>"
                    + "<tr><td height='10' width='10'>Battery_Level</td><td height='10' width='250' align='center'> "
                    + batteryStatus
                    + "</td></tr>"
                    // + batteryInfoPopupText
                    // + deviceValue.deviceBattery
                    // + "</td></tr>"
                    // + sensitivityInfoPopupText
                    // + deviceValue.deviceSensitivity
                    // + "</td></tr>"
                    // + linkQualityInfoPopupText
                    // + deviceValue.deviceLinkQualityIndicator
                    // + "</td></tr>"
                    // + signalStrengthInfoPopupText
                    // + deviceValue.deviceSignalStrength
                    // + "</td></tr>"
                    + "<tr><td height='10' width='10'>Latitude</td><td height='10' width='250'> "
                    + "<input type='text' name='latitudeField' id='latitudeField' style='width:70px;' value='" + parseFloat(deviceValue.deviceLocation.latitude) + "'>"
                    + "</td></tr>"

                    + "<tr><td height='10' width='10'>Longitude</td><td height='10' width='250'> "
                    + "<input type='text' name='longitudeField' id='longitudeField'  style='width:70px;' value='" + parseFloat(deviceValue.deviceLocation.longitude) + "'>"
                    + "</td></tr>"

                    + "<tr><td height='10' width='100%' colspan=2 >"
                    + "<input type='button' id='updateLocation' name='updateLocation' style='width:100%; margin: 0px 0px 0px 0px;' value='Update Location' onclick='cncDisplay.updateLocation(" + deviceValue.deviceId + ")'/> "
                    + "</td></tr>";

            if (deviceValue.deviceType != "C2I_SYSTEM" && deviceValue.deviceType != "ROUTER_NODE") {
                devicePopupHtml = devicePopupHtml + "<td> ";
                if (deviceValue.deviceStatus == "ON") {
                    devicePopupHtml = devicePopupHtml
                            + "<input type='button' id='displayTabVideoButton1'"
                            + "name='displayTabVideoButton' style='width:100%; margin: 0px 0px 0px 0px;' value='Disable' onclick='cncDisplay.turnOffDeviceById("
                            + deviceValue.deviceId + ")'/> ";
                }
                else {
                    devicePopupHtml = devicePopupHtml
                            + "<input type='button' id='displayTabVideoButton1'"
                            + "name='displayTabVideoButton2' style='width:100%; margin: 0px 0px 0px 0px;' value='Disable' disabled )'/>";
                }
                devicePopupHtml = devicePopupHtml + "</td>"
            }

            devicePopupHtml = devicePopupHtml
                    + "<td align='center' ";

            if (deviceValue.deviceType == "C2I_SYSTEM" || deviceValue.deviceType == "ROUTER_NODE") {
                devicePopupHtml = devicePopupHtml + " colspan=2 ";
            }

            devicePopupHtml = devicePopupHtml
                    + "> "
                    + "<input type='button' id='displayTabVideoButton2'"
                    + "name='displayTabVideoButton' style='width:100%; margin: 0px 0px 0px 0px;' value='Remove' onclick='cncDisplay.removeDeviceById("
                    + deviceValue.deviceId + ")'/> "
                    + "</td> </tr>"


//					+ "<tr><td height='10' width='10' colspan='2'>"
//					+ "<input type='button' id='explodeButtonId'"
//					+ "name='displayTabVideoButton' style='width:100%; margin: 0px 0px 0px 0px; background-color:red;' value='Explode' onclick='cncDisplay.authenticatePopup("
//					+ deviceValue.deviceId + "," + deviceValue.devicePhysicalAddress + ", " + deviceValue.networkId + " )'/> "
//					+ "</td></tr>"



            // if (deviceValue.deviceType == "VIDEO_CAMERA_NODE") {
            // devicePopupHtml = devicePopupHtml
            // + "<tr><td colspan='2' height='20'><center><input type='button'
            // id='displayInfowindowVideoButton'
            // name='displayInfowindowVideoButton' value='Play'
            // onclick='cncDisplay.displayInfowindowVideo(&quot;"
            // + deviceValue.deviceId
            // + "&quot;,"
            // + "&quot;"
            // + deviceValue.videoStreamLink
            // + "&quot;)'/><input type='button' id='displayTabVideoButton'
            // name='displayTabVideoButton' value='Play in Tab'
            // onclick='cncDisplay.displayTabVideo(&quot;"
            // + deviceValue.videoStreamLink + "&quot;)'/></center></td></tr>"
            // }

            devicePopupHtml = devicePopupHtml + "</table></font><div>"
        } else {
            devicePopupHtml = "No data found";
        }
        return devicePopupHtml;
    }

    function explodePopupHtmlForLeaflet(deviceValue) {

        if (deviceValue != null) {

            var deviceTypeStr;
            deviceTypeStr = getNodeStringByDeviceType(deviceValue.deviceType);

            var devicePopupHtml = "<div class='infowindow'><font size=2><table>"
//					+"<tr><td colspan='2' height='10' width='10'><center><b>"
//					+ deviceTypeStr
//					+ "</b></center></td></tr>"

                    + "<tr><td>"
                    + "<input type='button' id='explodeButtonIdSingle'"
                    + "name='displayExplodeButton' style='width:200; margin: 0px 0px 0px 0px; background-color:red;' value='Explode' onclick='cncDisplay.authenticatePopup("
                    + deviceValue.deviceId + "," + deviceValue.devicePhysicalAddress + ", " + deviceValue.networkId + " )'/> "
                    + "</td></tr>"


            devicePopupHtml = devicePopupHtml + "</table></font><div>"
        } else {
            devicePopupHtml = "No data found";
        }
        return devicePopupHtml;
    }


    function explosiveMineAuthentication(deviceValue) {
        var pwd = prompt('Please enter your password:', '');

        if (pwd != null && pwd == "test") {
            return generatePopupExplosiveMineLeaflet(deviceValue);
        }
    }
    /**
     * Popup Window for Explosive Mine
     */
    function generatePopupExplosiveMineLeaflet(deviceValue) {
        if (deviceValue != null) {

            if (deviceValue.deviceSignalStrength == null
                    || deviceValue.deviceSignalStrength == "") {
                deviceValue.deviceSignalStrength = "N/A";
            } else {
                deviceValue.deviceSignalStrength = deviceValue.deviceSignalStrength
                        + " dBm";
            }
            if (deviceValue.deviceSensitivity == null
                    || deviceValue.deviceSensitivity == "") {
                deviceValue.deviceSensitivity = "N/A";
            }
            if (deviceValue.deviceBattery == null
                    || deviceValue.deviceBattery == "") {
                deviceValue.deviceBattery = "N/A";
            }
            if (deviceValue.deviceLinkQualityIndicator == null
                    || deviceValue.deviceLinkQualityIndicator == "") {
                deviceValue.deviceLinkQualityIndicator = "N/A";
            }

            var deviceTypeStr;
            deviceTypeStr = getNodeStringByDeviceType(deviceValue.deviceType);


            var devicePopupHtml = "<div class='infowindow'><font size=2><table><tr><td colspan='2' height='10' width='10'><center><b>"
                    + deviceTypeStr
                    + "</b></center></td></tr>"

                    + "<tr><td height='10' width='10'>Device_Id</td><td height='10' width='250'  align='center'>  "
                    + deviceValue.deviceId
                    + "</td></tr>"

                    + "<tr><td>Network_Id</td><td height='10' width='250'  align='center'>"
                    + deviceValue.networkId
                    + "</td></tr>"

                    + "<tr><td height='10' width='10'>Status</td><td height='10' width='250' align='center'> "
                    + deviceValue.deviceStatus
                    + "</td></tr>"
                    // + batteryInfoPopupText
                    // + deviceValue.deviceBattery
                    // + "</td></tr>"
                    // + sensitivityInfoPopupText
                    // + deviceValue.deviceSensitivity
                    // + "</td></tr>"
                    // + linkQualityInfoPopupText
                    // + deviceValue.deviceLinkQualityIndicator
                    // + "</td></tr>"
                    // + signalStrengthInfoPopupText
                    // + deviceValue.deviceSignalStrength
                    // + "</td></tr>"
                    + "<tr><td height='10' width='10'>Location</td><td height='10' width='250'> "
                    + parseFloat(deviceValue.deviceLocation.latitude).toFixed(2)
                    + ", "
                    + parseFloat(deviceValue.deviceLocation.longitude).toFixed(2) + "</td></tr>"

                    + "<tr >";


            if (deviceValue.deviceType != "C2I_SYSTEM" && deviceValue.deviceType != "ROUTER_NODE") {
                devicePopupHtml = devicePopupHtml + "<td> ";
                if (deviceValue.deviceStatus == "ON") {
                    devicePopupHtml = devicePopupHtml
                            + "<input type='button' id='displayTabVideoButton1'"
                            + "name='displayTabVideoButton' style='width:70px; margin: 0px 0px 0px 0px;' value='Disable' onclick='cncDisplay.turnOffDeviceById("
                            + deviceValue.deviceId + ")'/> ";
                }
                else {
                    devicePopupHtml = devicePopupHtml
                            + "<input type='button' id='displayTabVideoButton1'"
                            + "name='displayTabVideoButton2' style='width:70px; margin: 0px 0px 0px 0px;' value='Disable' disabled )'/>";
                }
                devicePopupHtml = devicePopupHtml + "</td>"
            }



            devicePopupHtml = devicePopupHtml
                    + "<td align='center' ";

            if (deviceValue.deviceType == "C2I_SYSTEM" || deviceValue.deviceType == "ROUTER_NODE") {
                devicePopupHtml = devicePopupHtml + " colspan=2 ";
            }

            devicePopupHtml = devicePopupHtml
                    + "> "
                    + "<input type='button' id='displayTabVideoButton2'"
                    + "name='displayTabVideoButton' style='width:70px; margin: 0px 0px 0px 0px;' value='Remove' onclick='cncDisplay.removeDeviceById("
                    + deviceValue.deviceId + ")'/> "
                    + "</td> </tr>"



                    + "<tr><td height='10' width='10' colspan='2'>"
                    + "<input type='button' id='explodeButtonId'"
                    + "name='displayTabVideoButton' style='width:100%; margin: 0px 0px 0px 0px; background-color:red;' value='Explode' onclick='cncDisplay.authenticatePopup("
                    + deviceValue.deviceId + "," + deviceValue.devicePhysicalAddress + ", " + deviceValue.networkId + " )'/> "
                    + "</td></tr>"


            // if (deviceValue.deviceType == "VIDEO_CAMERA_NODE") {
            // devicePopupHtml = devicePopupHtml
            // + "<tr><td colspan='2' height='20'><center><input type='button'
            // id='displayInfowindowVideoButton'
            // name='displayInfowindowVideoButton' value='Play'
            // onclick='cncDisplay.displayInfowindowVideo(&quot;"
            // + deviceValue.deviceId
            // + "&quot;,"
            // + "&quot;"
            // + deviceValue.videoStreamLink
            // + "&quot;)'/><input type='button' id='displayTabVideoButton'
            // name='displayTabVideoButton' value='Play in Tab'
            // onclick='cncDisplay.displayTabVideo(&quot;"
            // + deviceValue.videoStreamLink + "&quot;)'/></center></td></tr>"
            // }

            devicePopupHtml = devicePopupHtml + "</table></font><div>"
        } else {
            devicePopupHtml = "No data found";
        }
        return devicePopupHtml;
    }


    /******************************************************************
     * Function to get the Device Type on basis of Device Type stored
     * in Database
     ******************************************************************/
    function getNodeStringByDeviceType(deviceType) {
        var deviceTypeStr;
        if (deviceType == "C2I_SYSTEM") {
            deviceTypeStr = "C2I System";
        }
        else if (deviceType == "PIR_SENSOR_NODE") {
            deviceTypeStr = "PIR Sensor";
        }
        else if (deviceType == "DUAL_SENSOR_NODE") {
            deviceTypeStr = "DUAL Mode Sensor";
        }
        else if (deviceType == "SEISMIC_SENSOR_NODE") {
            deviceTypeStr = "Seismic Sensor";
        }
        else if (deviceType == "ROUTER_NODE") {
            deviceTypeStr = "Router";
        }
        else if (deviceType == "VIDEO_CAMERA_NODE") {
            deviceTypeStr = "Camera";
        }
        else if (deviceType == "GATEWAY_NODE") {
            deviceTypeStr = "Gateway";
        }
        else if (deviceType == "ROUTER_NODE") {
            deviceTypeStr = "Router";
        }
        else if (deviceType == "HUMAN_ATTACHED") {
            deviceTypeStr = "Human Attached";
        }
        else if (deviceType == "EXPLOSIVE_MINE") {
            deviceTypeStr = "Explosive Mine";
        }


        return deviceTypeStr;
    }

    /*
     * Function to play the video stream inside marker infowindow
     */
    function playInfowindowVideo(deviceId, videoStreamLink) {

        var infowindowVideoHtml = "<div class='infowindow-video'><center>Live stream from "
                + deviceId + "</center>" + videoHtml + "</div>";
        var infowindowId = deviceId + "-infowindow";
        infowindow = $("#map-div").gmap3({
            get: {
                id: infowindowId
            }
        });
        /*
         * Cleanup streaming video after infowindow closes since HTML5 does not
         * provide a stop function or any functionality to cleanly stop the
         * streaming video. This is implemented to ensure that the streaming
         * link is stopped and does not keep running and buffering in the
         * background when the infowindow closes
         */
        google.maps.event.addListener(infowindow, 'closeclick', function () {
            var video = document.getElementById("infowindow-video-player");
            video.pause();
            video.src = "";
            video.load();
        });
        infowindow.setContent(infowindowVideoHtml);
        var video = document.getElementById("infowindow-video-player");
        video.src = videoStreamLink;
        video.load();
    }

    /*
     * Function to play the video stream inside video tab
     */
    function playTabVideo(videoStreamLink) {
        $("#main-display-tabs").tabs("option", "active", 6);
        var video = document.getElementById("tab-video-player");
        video.src = videoStreamLink;
        video.load();
    }

    /*
     * Function to initialize date and time picker add on
     */
    function setDatePicker() {
        $('#startDate').datetimepicker({
            controlType: 'select',
            dateFormat: 'mm-dd-yy',
            timeFormat: 'hh:mm:ss tt'
        });
        $('#endDate').datetimepicker({
            controlType: 'select',
            dateFormat: 'mm-dd-yy',
            timeFormat: 'hh:mm:ss tt'
        });
        var startDateTextBox = $('#startDate');
        var endDateTextBox = $('#endDate');

        /*
         * Checking for start date greater than end date in which case end date
         * will be replaced by start date.
         */
        startDateTextBox
                .datetimepicker({
                    onClose: function (dateText, inst) {
                        if (endDateTextBox.val() != '') {
                            var testStartDate = startDateTextBox
                                    .datetimepicker('getDate');
                            var testEndDate = endDateTextBox
                                    .datetimepicker('getDate');
                            if (testStartDate > testEndDate)
                                endDateTextBox.datetimepicker('setDate',
                                        testStartDate);
                        } else {
                            endDateTextBox.val(dateText);
                        }
                    },
                    onSelect: function (selectedDateTime) {
                        endDateTextBox.datetimepicker('option', 'minDate',
                                startDateTextBox.datetimepicker('getDate'));
                    }
                });
        endDateTextBox
                .datetimepicker({
                    onClose: function (dateText, inst) {
                        if (startDateTextBox.val() != '') {
                            var testStartDate = startDateTextBox
                                    .datetimepicker('getDate');
                            var testEndDate = endDateTextBox
                                    .datetimepicker('getDate');
                            if (testStartDate > testEndDate)
                                startDateTextBox.datetimepicker('setDate',
                                        testEndDate);
                        } else {
                            startDateTextBox.val(dateText);
                        }
                    },
                    onSelect: function (selectedDateTime) {
                        startDateTextBox.datetimepicker('option', 'maxDate',
                                endDateTextBox.datetimepicker('getDate'));
                    }
                });
    }

    /*
     * Function to change the marker location of a device when a location
     * message is received. The sensor icon is mapped in real time on the map
     * with the new location.
     */
    function changeMarkerLocation(deviceId, location, deviceType) {

        var moving_target_beep_url = audioDirectoryLocation + "moving_target_beep.wav";
        var moving_target_beep = new Audio(moving_target_beep_url);
        var latlng = location.split(",");
        try {
            var myLatlng = new google.maps.LatLng(latlng[0], latlng[1]);
        } catch (error) {
            console.log("Error creating google map latlng object: " + error);
        }
        // Get the marker for the device with changed location
        var deviceMarker = $("#map-div").gmap3({
            get: {
                id: deviceId
            }
        });
        // If device marker is already plotted, set the new position of the
        // marker on the map
        if (deviceMarker) {
            // Set the new position for the device
            deviceMarker.setPosition(myLatlng);
            var deviceMarkerCircle = null;
            if (deviceType == "PIR_SENSOR_NODE" ||
                    deviceType == "DUAL_SENSOR_NODE" ||
                    deviceType == "SEISMIC_SENSOR_NODE" ||
                    deviceType == "HUMAN_ATTACHED") {
                var circleId = deviceId + "-circle";
                deviceMarkerCircle = $("#map-div").gmap3({
                    get: {
                        id: circleId
                    }
                });
            }
            // If the circle around the device marker exists, set the new
            // position of the
            // circle along with the device marker. This circle should always
            // exist for
            // sensor nodes but we check for it's existence anyway to be safe
            if (deviceMarkerCircle) {
                deviceMarkerCircle.setCenter(myLatlng);
            }
            if (deviceType == "PIR_SENSOR_NODE" ||
                    deviceType == "DUAL_SENSOR_NODE" ||
                    deviceType == "SEISMIC_SENSOR_NODE" ||
                    deviceType == "HUMAN_ATTACHED") {
                moving_target_beep.play();
            }
        } else {
            // Add the new device to the dropdown
            $("#deviceListDropdown").append($("<option>", {
                text: deviceId,
                value: deviceId
            }));
            // create and plot the marker for the new device
            createAndPlotMarker(deviceId, latlng, deviceType);

        }
    }

    /*
     * Function to change the marker location of a device when a location
     * message is received. The sensor icon is mapped in real time on the map
     * with the new location.
     */
    function changeMarkerLocationForLeaflet(deviceId, location, deviceType) {

        var myLatLng;

        var moving_target_beep_url = audioDirectoryLocation + "moving_target_beep.wav";
        var moving_target_beep = new Audio(moving_target_beep_url);
        var latlng = location.split(",");
        try {
            myLatLng = new L.LatLng(latlng[0], latlng[1]);
            //var myLatlng = new google.maps.LatLng(latlng[0], latlng[1]);
        } catch (error) {
            console.log("Error creating Leaflet map latlng object: " + error);
        }

        // Get the marker for the device with changed location
        var deviceMarker;
        for (var index in markerArray) {
            var keyValue = markerArray[index];
            if (keyValue[0] == deviceId) {
                deviceMarker = keyValue[1];
            }
        }

        // If device marker is already plotted, set the new position of the
        // marker on the map
        if (deviceMarker) {
            // Set the new position for the device
            deviceMarker.setLatLng(myLatLng);

            // Playing the beep
            if (deviceType == "PIR_SENSOR_NODE" ||
                    deviceType == "DUAL_SENSOR_NODE" ||
                    deviceType == "SEISMIC_SENSOR_NODE" ||
                    deviceType == "HUMAN_ATTACHED") {
                moving_target_beep.play();
            }
        } else {
            // Add the new device to the dropdown
            $("#deviceListDropdown2").append($("<option>", {
                text: deviceId + " -- " + getNodeStringByDeviceType(deviceType),
                value: deviceId
            }));

            // create and plot the marker for the new device
            createAndPlotMarkerForLeaflet(deviceId, latlng, deviceType);

        }
    }

    /*
     * Function to display an alert info window popup on a sensor icon when an
     * alert message ahs been received from that sensor. This function also
     * plays an alert sound
     */
    function generateAlert(deviceId) {

        var deviceMap = $("#map-div").gmap3("get");
        var alert_beep_url = audioDirectoryLocation + "alert.wav";
        var alert_beep = new Audio(alert_beep_url);
        var warning_beep_url = audioDirectoryLocation + "warning.wav";
        var warning_beep = new Audio(warning_beep_url);

        if (userRole == "ONSITE_USER" || userRole == "COMMANDER"
                || userRole == "BATTALION_COMMANDER") {
            alertCounter++;
            noOfAlertsPending++;
            var alertText = "<div class='infowindow-alert'><center><img src='"
                    + imageDirectoryLocation
                    + "siren.gif' /></center>"
                    + "<center>DEVICE ID: </font>"
                    + deviceId
                    + "<br><center>ALERT#"
                    + alertCounter
                    + "<input type='button' value='Accept' onclick='cncDisplay.closeInfowindow(&quot;"
                    + deviceId + "&quot;)'</center></div>";
        } else if (userRole == "FIELD_USER") {
            var alertText = "<center><img src='"
                    + imageDirectoryLocation
                    + "siren.gif' /></center>"
                    + "<br><b><center>ALERT ASSIGNED<br>TAKE ACTION SOLDIER'!!!</center><b>";
        } else if (userRole == "BATTALION_SUPPORT") {
            var alertText = "<div class='infowindow-warning'><center><img src='"
                    + imageDirectoryLocation
                    + "warning.png'/></center>"
                    + "<b><center>WARNING<br>THREAT DETECTED'!!!</center><b></div>";
        }
        var alertMarker = $("#map-div").gmap3({
            get: {
                id: deviceId
            }
        });

        // Change the static sensor icon to an animated sensor icon
        alertMarker.setIcon(animatedSensorIcon);
        // deviceMap.panTo(alertMarker.getPosition());

        // Creating an Id for alert infowindow corresponding to the device from
        // which the alert is generated from
        var alertInfowindowId = deviceId + "-alertwindow";

        // Create and display a new alert infowindow for the device marker from
        // which alert message has been received
        var alertInfowindow = $("#map-div").gmap3({
            get: {
                id: alertInfowindowId
            }
        });
        if (alertInfowindow) {
            if (alertInfowindow.opened == false) {
                alertInfowindow.setContent(alertText);
                alertInfowindow.open(deviceMap, alertMarker);
                alertInfowindow.opened = true;
                if (userRole != "BATTALION_SUPPORT") {
                    alert_beep.play();
                } else {
                    warning_beep.play();
                }
            } else {
                alertInfowindow.setContent(alertText);
            }
        } else {
            $("#map-div").gmap3({
                infowindow: {
                    id: alertInfowindowId,
                    anchor: alertMarker,
                    options: {
                        content: alertText,
                        maxWidth: 200
                    },
                    events: {
                        closeclick: function () {
                            var closedInfowindow = $(this).gmap3({
                                get: {
                                    id: alertInfowindowId
                                }
                            });
                            var alertMarker = $(this).gmap3({
                                get: {
                                    id: deviceId
                                }
                            });
                            alertMarker.setIcon(staticSensorIcon);
                            closedInfowindow.opened = false;
                        }
                    }
                }
            });

            alertInfowindow = $("#map-div").gmap3({
                get: {
                    id: alertInfowindowId
                }
            });
            alertInfowindow.opened = true;
            if (userRole != "BATTALION_SUPPORT") {
                alert_beep.play();
            } else {
                warning_beep.play();
            }
        }
    }

    /***************************************************************************
     * Generate Alert for Leaflet.
     */
    function generateAlertForLeaflet(deviceId) {

        try {
            var popup;

            var alert_beep_url = audioDirectoryLocation + "alert.wav";
            var alert_beep = new Audio(alert_beep_url);



            var warning_beep_url = audioDirectoryLocation + "warning.wav";
            var warning_beep = new Audio(warning_beep_url);
            var alertText;
            if (userRole == "ONSITE_USER" || userRole == "COMMANDER"
                    || userRole == "BATTALION_COMMANDER") {
                alertCounter++;
                noOfAlertsPending++;
                alertText = "<div class='infowindow-alert'><center><img src='"
                        + imageDirectoryLocation + "siren.gif' /></center>"
                        + "<center></font>" + "<b>" + deviceId + "</b>";
                // + "<br><center>Alert # "
                // + "<b>" + alertCounter + "</b>"
                // + "<input type='button' value='Hide'
                // onclick='cncDisplay.closeInfowindowForLeaflet(&quot;"
                // + deviceId + "&quot;)'</center></div>";
            } else if (userRole == "FIELD_USER") {
                alertText = "<center><img src='"
                        + imageDirectoryLocation
                        + "siren.gif' /></center>"
                        + "<br><b><center>ALERT ASSIGNED<br>TAKE ACTION SOLDIER'!!!</center><b>";
            } else if (userRole == "BATTALION_SUPPORT") {
                alertText = "<div class='infowindow-warning'><center><img src='"
                        + imageDirectoryLocation
                        + "warning.png'/></center>"
                        + "<b><center>WARNING<br>THREAT DETECTED'!!!</center><b></div>";
            }

            var alertMarker;

            for (var index in markerArray) {
                var keyValue = markerArray[index];

                if (keyValue[0] == deviceId) {
                    alertMarker = keyValue[1];
                }

            }

            var dynamicIcon = L.icon({
                iconUrl: sensorAnimatedMarker,
                iconSize: [71, 67]

            });
            var staticIcon = L.icon({
                iconUrl: sensorStatusOnMarker,
                iconSize: [50, 50]
            });
            alertMarker.setIcon(dynamicIcon);
            setTimeout(function () {
                alertMarker.setIcon(staticIcon);
            }, 12000);

            // Getting Device Data to again display information as Popup
            var deviceData = cncDisplay.getDeviceById(deviceId);
            var InfowindowData = generatePopupHtmlForLeaflet(deviceData.device);

            // On close retreive the previous Popup
//			popup.on('close', function() {
//				alertMarker.bindPopup(InfowindowData)
//			});


            if (userRole != "BATTALION_SUPPORT") {
                alert_beep.play();
            } else {
                warning_beep.play();
            }

            // alertMarker.on('mouseover',
            // function(){alertMarker.bindPopup(alertText).openPopup();});
            // alertMarker.on('mouseout',
            // function(){alertMarker.closePopup();});

        } catch (error) {
            console.log("Error: " + error);
        }

    }

    // Auto close the alert popup after the specified time in milliseconds
    // and also change the animated sensor icon back to a static sensor icon
    function closeAlertInfowindow(deviceId) {
        var alertInfowindowId = deviceId + "-alertwindow";
        var infowindow = $("#map-div").gmap3({
            get: {
                id: alertInfowindowId
            }
        });
        google.maps.event.trigger(infowindow, 'closeclick');
        infowindow.close();
    }

    // When user clicks Accept button on Alert
    function closeAlertInfowindowForLeaflet(deviceId) {

        var deviceData = cncDisplay.getDeviceById(deviceId);
        var InfowindowData = generatePopupHtmlForLeaflet(deviceData.device);

        var alertMarker;
        for (var index in markerArray) {
            var keyValue = markerArray[index];

            if (keyValue[0] == deviceId) {
                alertMarker = keyValue[1];
            }

        }
        alertMarker.closePopup();
        // var popupText =
        // L.popup().setContent(InfowindowData);//.openOn(alertMarker);
        // alertMarker.bindPopup(popupText).closePopup();//.openPopup();

    }
    /*
     * Function to create a Message object from a JSON message
     */
    function Message(message) {

        for (var property in message) {
            if (property == "messageDate") {
                this[property] = new Date(message[property]);
            } else {
                this[property] = message[property];
            }
        }
    }

    function setInt() {

    }

    /*
     * Function to process the message received from the C2I application server.
     * If the message is of the type ALERT, an alert popup is displayed on the
     * marker for that deviced and alert siren is played. If the message is of
     * the type LOCATION, the location of the marker for that device is changed
     * in real time on the map
     */
    function processMessage(message) {

        var alertIcon = null;
        var deviceId;
        var consoleDisplayMessage = "Console Message";
        var activeTab = $("#main-display-tabs").tabs("option", "active");
        var msg;
        try {

            msg = new Message(JSON.parse(message.body));

            messageType = msg.messageType;
            consoleDisplayMessage = msg.messageDate + "> " + msg.messageContent;

            // Getting Device Data to again display information as Popup
            var deviceData = cncDisplay.getDeviceById(msg.messageDeviceId);
            var infowindowData = generatePopupHtmlForLeaflet(deviceData.device);

            var latStr = parseFloat(deviceData.device.deviceLocation.latitude).toFixed(4)
            var longStr = parseFloat(deviceData.device.deviceLocation.longitude).toFixed(4)

            /** 
             * Inactive Device to make it Disable at run time 
             */
            var inactiveDevices = msg.inactiveDevices;
            // String[] inactiveDeviceArray = in
            var inactiveDevicesArray = inactiveDevices.split(",");
            for (i = 0; i < inactiveDevicesArray.length; i++) {
                var inactiveDeviceId = inactiveDevicesArray[i];
                turnOffDeviceByIdFunctionOnIcon(inactiveDeviceId);
            }





            if ((msg.messageType == "ALERT" || msg.messageType == "INVALID")
                    && (msg.messageDeviceType == "PIR_SENSOR_NODE" || msg.messageDeviceType == "DUAL_SENSOR_NODE" ||
                            msg.messageDeviceType == "SEISMIC_SENSOR_NODE")
                    ) {

                // Getting Specific Alert Marker
                var alertMarker;

                for (var index in markerArray) {
                    var keyValue = markerArray[index];
                    if (keyValue[0] == msg.messageDeviceId) {
                        alertMarker = keyValue[1];
                    }
                }

                if (userRole == "COMMANDER" || userRole == "ONSITE_USER") {
                    // Make the alert tab blink when an alert has been received
                    if (activeTab != 2) {
                        $blinker.blinker('start');
                    }

                    // generateAlert(msg.messageDeviceId);
                    generateAlertForLeaflet(msg.messageDeviceId);

                    if (msg.explode) {
                        var deviceData2 = cncDisplay.getDeviceById(msg.messageDeviceId);
                        var explodePopup = explodePopupHtmlForLeaflet(deviceData2.device);
                        alertMarker.closePopup();
                        alertMarker.bindPopup(explodePopup).openPopup();
                        setTimeout(function () {
                            alertMarker.closePopup();
                            alertMarker.bindPopup(infowindowData); // binding the main information popup

                        }, 120000);

                    }


                    // On Alert Setting the Map Center and Zoom Level
                    map.setView(new L.LatLng(latStr, longStr), 18);

                    var alertDate = new Date(msg.messageDate);
                    var alertData = alertDate + " Device Id: "
                            + msg.messageDeviceId;

                    //alert(alertData);

                    $("#receivedAlertDeviceId").append($("<option>", {
                        text: alertData,
                        value: msg.messageDeviceId
                    }));
                    $("#receivedAlertDeviceId option").css("background-color",
                            "red");

                    // Setting no of Pending Alerts and Last Voilation Device ID at Header
                    noOfAlertsPending = receivedAlertDeviceId.length;
                    $('#noOfAlertsPending').text(noOfAlertsPending);
                    $('#lastAlertDeviceId').text(msg.messageDeviceId);
                    $('#lastAlertTime').text(alertDate.toLocaleString());


                    $('#lastAlertLatLong').text("Lat: " + latStr + " --- Long: " + longStr);

                    /******************************************************************************************
                     * Block for Seismic either alert from Human, Vehicle
                     * or Flying Target
                     *****************************************************************************************/
                    if (msg.messageDeviceType == "SEISMIC_SENSOR_NODE") {


                        if (msg.messageRawData == "HU") {
                            //$('#alertTypeInfoDiv').text("Human voilation at: " + msg.messageDeviceId);
                            $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "human_wireframe_animated.gif) no-repeat");
                            $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");

                            var $timeoutHandle1 = 0;

                            setTimeout(function () {

                                $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "blinking_radar.modified.gif) no-repeat");
                                $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");
                            }, 60000);
                            //clearTimeout($timeoutHandle1);
                        }
                        else if (msg.messageRawData == "LV") {
                            //$('#alertTypeInfoDiv').text("Light Vehicle voilation at: " + msg.messageDeviceId);
                            $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "hilux2.jpg) no-repeat");
                            $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");

                            var $timeoutHandle2 = 0;

                            setTimeout(function () {
                                $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "blinking_radar.modified.gif) no-repeat");
                                $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");
                            }, 60000);
                            //clearTimeout($timeoutHandle2);
                        }
                        else if (msg.messageRawData == "HV") {
                            //$('#alertTypeInfoDiv').text("Heavy Vehicle voilation at: " + msg.messageDeviceId);
                            $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "tank2.jpg) no-repeat");
                            $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");

                            var $timeoutHandle3 = 0;

                            setTimeout(function () {
                                $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "blinking_radar.modified.gif) no-repeat");
                                $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");
                            }, 60000);
                            //clearTimeout($timeoutHandle3);
                        }
                        else if (msg.messageRawData == "FT") {
                            //$('#alertTypeInfoDiv').text("Flying Target voilation at: " + msg.messageDeviceId);
                            $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "flying_target_animated.jpg) no-repeat");
                            $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");

                            var $timeoutHandle4 = 0;

                            setTimeout(function () {
                                $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "blinking_radar.modified.gif) no-repeat");
                                $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");
                            }, 60000);
                            //clearTimeout($timeoutHandle4);
                        }

                        // Play sound on msg 
                        var moving_target_beep_url = audioDirectoryLocation + "moving_target_beep.wav";
                        var moving_target_beep = new Audio(moving_target_beep_url);
                        moving_target_beep.play();

                    }
                    else {
                        //$('#alertTypeInfoDiv').text("Last Alert from Device: " + msg.messageDeviceId);
                        $('#alertTypeInfoDiv').parent().css("background", "url(" + imageDirectoryLocation + "blinking_radar.modified.gif) no-repeat");
                        $('#alertTypeInfoDiv').parent().css("background-size", "150px 110px");

                    }

                } else if (userRole == "BATTALION_COMMANDER"
                        || userRole == "BATTALION_SUPPORT") {

                    // generateAlert(msg.messageDeviceId);
                    generateAlertForLeaflet(msg.messageDeviceId);

                }
            } else if (msg.messageType == "LOCATION") {

                // Getting Specific Alert Marker
                var alertMarker;
                for (var index in markerArray) {
                    var keyValue = markerArray[index];
                    if (keyValue[0] == msg.messageDeviceId) {
                        alertMarker = keyValue[1];
                    }
                }

                if (msg.messageId != null) {
                    changeMarkerLocationForLeaflet(msg.messageDeviceId,
                            msg.messageRawData, msg.messageDeviceType);

                    alertMarker.closePopup();
                    alertMarker.bindPopup("Device ID : " + msg.messageDeviceId + " Location changed").openPopup();
                    clearTimeout(statusMessagePopupTimer);
                    setTimeout(function () {
                        alertMarker.closePopup();
                        alertMarker.bindPopup(infowindowData); // binding the main information popup
                    }, 3000);



                }
            } else if (msg.messageType == "ALERT_ACCEPTED"
                    && (userRole == "COMMANDER" || userRole == "ONSITE_USER")
                    && msg.messageRawData != username) {

                // Getting Specific Alert Marker
                var alertMarker;
                for (var index in markerArray) {
                    var keyValue = markerArray[index];
                    if (keyValue[0] == msg.messageDeviceId) {
                        alertMarker = keyValue[1];
                    }
                }


                $("#receivedAlertDeviceId option").each(function () {
                    if ($(this).val() == msg.messageDeviceId) {
                        $(this).remove();

                    }
                });
            } else if (msg.messageType == "ALERT_ASSIGNED"
                    && userRole == "FIELD_USER"
                    && msg.messageRawData == username) {

                // Getting Specific Alert Marker
                var alertMarker;
                for (var index in markerArray) {
                    var keyValue = markerArray[index];
                    if (keyValue[0] == msg.messageDeviceId) {
                        alertMarker = keyValue[1];
                    }
                }


                if (activeTab != 2) {
                    $blinker.blinker('start');
                }

                // generateAlert(msg.messageDeviceId);
                generateAlertForLeaflet(msg.messageDeviceId);

                var dateReceived = new Date(msg.messageDate);
                alertData = dateReceived + " Device Id: " + msg.messageDeviceId;
                $("#assignedAlertDeviceId").append($("<option>", {
                    text: alertData,
                    value: msg.messageDeviceId
                }));
                $("#assignedAlertDeviceId option").css("background-color",
                        "yellow");

            }

            /**************************************************************************
             * STATUS MESSAGES
             * When new Device is ON/Off/GPS Locking/GPS Locking Failed all
             * messages are being displayed
             **************************************************************************/

            else if (msg.messageType == "STATUS") {


                // Getting Specific Marker
                var alertMarker;
                for (var index in markerArray) {
                    var keyValue = markerArray[index];
                    if (keyValue[0] == msg.messageDeviceId) {
                        alertMarker = keyValue[1];
                    }
                }

                // Status ON
                if (msg.messageRawData == "1") {
                    alertMarker.closePopup();
                    alertMarker.bindPopup("Device ID : " + msg.messageDeviceId + " is Connected now").openPopup();
                    clearTimeout(statusMessagePopupTimer);
                    statusMessagePopupTimer = setTimeout(function () {
                        alertMarker.closePopup();
                        alertMarker.bindPopup(infowindowData); // binding the main information popup
                    }, 3000);


                    // Add the On Status Marker
                    var greenIcon;
                    greenIcon = L.icon({
                        iconUrl: sensorStatusOnMarker,
                        iconSize: [50, 50]
                    });
                    alertMarker.setIcon(greenIcon);

                    // On Alert Setting the Map Center and Zoom Level
                    map.setView(new L.LatLng(latStr, longStr), 18);


                }
                // Status OFF
                else if (msg.messageRawData == "0") {
                    alertMarker.closePopup();
                    alertMarker.bindPopup(
                            "Device ID : " + msg.messageDeviceId
                            + " is OFF now").openPopup();
                    clearTimeout(statusMessagePopupTimer);
                    statusMessagePopupTimer = setTimeout(function () {
                        alertMarker.closePopup();
                        alertMarker.bindPopup(infowindowData); // binding the main information popup
                    }, 3000);

                    // Add the On Status Marker
                    var greenIcon;
                    greenIcon = L.icon({
                        iconUrl: sensorStatusOffMarker,
                        iconSize: [50, 50]
                    });
                    alertMarker.setIcon(greenIcon);
                }
                // Locking GPS
                else if (msg.messageRawData == "9") {
                    alertMarker.closePopup();
                    alertMarker.bindPopup(
                            "Device ID : " + msg.messageDeviceId
                            + " is locking GPS now").openPopup();
                    clearTimeout(statusMessagePopupTimer);
                    statusMessagePopupTimer = setTimeout(function () {
                        alertMarker.closePopup();
                        alertMarker.bindPopup(infowindowData); // binding the main information popup
                    }, 3000);

                    // Add the On Status Marker
                    var greenIcon;
                    greenIcon = L.icon({
                        iconUrl: sensorStatusOnMarker,
                        iconSize: [50, 50]
                    });
                    alertMarker.setIcon(greenIcon);



                }
                // GPS Locking Failed
                else if (msg.messageRawData == "10") {
                    alertMarker.closePopup();
                    alertMarker.bindPopup(
                            "Device ID : " + msg.messageDeviceId
                            + " has failed in locking GPS").openPopup();
                    clearTimeout(statusMessagePopupTimer);
                    statusMessagePopupTimer = setTimeout(function () {
                        alertMarker.closePopup();
                        alertMarker.bindPopup(infowindowData); // binding the main information popup
                    }, 3000);

                    // Add the On Status Marker
                    var greenIcon;
                    greenIcon = L.icon({
                        iconUrl: sensorStatusOnMarker,
                        iconSize: [50, 50]
                    });
                    alertMarker.setIcon(greenIcon);
                }

                // var w = window.open('','','width=350,height=100');
                // w.document.write('Device ID : ' + msg.messageDeviceId + ' is
                // connected');
                // w.focus();
                // setTimeout(function() {w.close();}, 5000);

                var moving_target_beep_url = audioDirectoryLocation + "moving_target_beep.wav";
                var moving_target_beep = new Audio(moving_target_beep_url);
                moving_target_beep.play();
            }
            else if (msg.messageType == "HEART_BEAT") {

                // Getting Specific Alert Marker
                var alertMarker;
                for (var index in markerArray) {
                    var keyValue = markerArray[index];
                    if (keyValue[0] == msg.messageDeviceId) {
                        alertMarker = keyValue[1];
                    }
                }
                alertMarker.closePopup();
                var poppp = alertMarker.bindPopup("Device ID : " + msg.messageDeviceId + " heart beat received").openPopup();
                setTimeout(function () {
                    alertMarker.closePopup();
                    alertMarker.bindPopup(infowindowData); // binding the main information popup
                }, 3000);
            }

            /** 
             * Battery Message 
             */
            else if (msg.messageType == "BATTERY") {

                // Getting Specific Alert Marker
                var alertMarker;
                for (var index in markerArray) {
                    var keyValue = markerArray[index];
                    if (keyValue[0] == msg.messageDeviceId) {
                        alertMarker = keyValue[1];
                    }
                }

                alertMarker.closePopup();
                var poppp = alertMarker.bindPopup("Battery status of device " + msg.messageDeviceId + " : " + msg.batteryStatus).openPopup();
                clearTimeout(statusMessagePopupTimer);
                statusMessagePopupTimer = setTimeout(function () {
                    alertMarker.closePopup();
                    alertMarker.bindPopup(infowindowData); // binding the main information popup
                }, 3000);

                // Play sound on msg 
                var moving_target_beep_url = audioDirectoryLocation + "moving_target_beep.wav";
                var moving_target_beep = new Audio(moving_target_beep_url);
                moving_target_beep.play();
            }
            else if (msg.messageType == "REGISTERED") {

                // Getting Specific Alert Marker
                var alertMarker;
                for (var index in markerArray) {
                    var keyValue = markerArray[index];
                    if (keyValue[0] == msg.messageDeviceId) {
                        alertMarker = keyValue[1];
                    }
                }

                alertMarker.closePopup();
                alertMarker.bindPopup("Device ID : " + msg.messageDeviceId + " is authenticated.").openPopup();

                clearTimeout(statusMessagePopupTimer);
                statusMessagePopupTimer = setTimeout(function () {
                    alertMarker.closePopup();
                    alertMarker.bindPopup(infowindowData); // binding the main information popup
                }, 3000);


                // Play sound on msg 
                var moving_target_beep_url = audioDirectoryLocation + "moving_target_beep.wav";
                var moving_target_beep = new Audio(moving_target_beep_url);
                moving_target_beep.play();
            }
        } catch (exception) {
            console.log("Error processing the received JSON message: "
                    + exception);
        }

        return consoleDisplayMessage;
    }

    function displayMessage(console) {

        var div = $(console + ' div');

        var print = function (message, output) {

            if (messageType == "ALERT" && output == "to_console") {
                div.append($("<font color='red'>").text(message));
                div.append($("<br>").text(' '));
            } else if ((messageType == "ALERT_ASSIGNED"
                    || messageType == "ALERT_ACCEPTED" || messageType == "ALERT_RESOLVED")
                    && output == "to_console") {
                div.append($("<font color='orange'>").text(message));
                div.append($("<br>").text(' '));
            } else if (messageType == "INVALID" && output == "to_console") {
                div.append($("<font color='yellow'>").text(message));
                div.append($("<br>").text(' '));
            } else {
                div.append($("<font color='white'>").text(message));
                div.append($("<br>").text(' '));
            }
            div.scrollTop(div.prop("scrollHeight"));
            // $('#div1').scrollTop($('#div1').prop("scrollHeight"));
        }

        return print;
    }

    function assignAlertFunction() {
        // alert("Inside assign Alert Function");
        noOfAlertsPending = receivedAlertDeviceId.length;
        if (noOfAlertsPending > 0) {
            $('#noOfAlertsPending').text(noOfAlertsPending - 1);
            $('#noOfAlertsPending').blinker('start');
        }

        else {
            $('#noOfAlertsPending').text('0');
            $('#noOfAlertsPending').blinker('stop');
        }

    }

    function runNtvdFunction() {
        // alert("Run NTVD Clicked");

        // $.ajax({
        // type: "POST",
        // url: "python /opt/meg/ntvd/gui/clean/ntvdapp.v6.py",
        // data: { param: text}
        // }).done(function( o ) {
        // // do something
        // });
    }

    /*******************************************************
     * Function to Remove Device by ID when user clicks on
     * Remove button in Popup HTML Box
     *******************************************************/
    function removeDeviceByIdFunction(deviceId) {

        var requestUrl = "/cnc/device/delete";
        var sendData = {
            deviceIdDeleteParam: deviceId
        };
        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData, requestTypeGet, receiveTypeText, function (response, status, error) {
        });

        // Getting Specific Alert Marker
        var alertMarker;
        for (var index in markerArray) {
            var keyValue = markerArray[index];
            if (keyValue[0] == deviceId) {
                alertMarker = keyValue[1];
            }
        }

        map.removeLayer(alertMarker);


    }

    function authenticatePopupFunction(deviceId, devicePhysicalAddress, networkId) {
        var password = prompt("Please enter your password:");
        if (password == 'ugs') {

            if (confirm("Are you sure you want to explode?")) {
                var password2 = prompt("Please enter your password again:");

                if (password2 == 'ugs') {

                    alert("Exploded");


                    var requestUrl = "/cnc/device/explode";
                    var sendData = {
                        deviceIdParam: deviceId,
                        devicePhysicalAddressParam: devicePhysicalAddress,
                        networkIdParam: networkId

                    };
                    cncDisplay.ExecuteAjaxRequest(requestUrl, sendData, requestTypeGet, receiveTypeText, function (response, status, error) {

                    });


                }
            }
        }
        else {
            alert("Wrong Password. Try again!");
        }
    }

    /** 
     * When alert received and any other device last alert time is more than 
     * specified time then make it Off 
     */
    function turnOffDeviceByIdFunctionOnIcon(deviceId) {
        try {
            // Getting Specific Alert Marker

            var alertMarkerTemp;
            for (var index in markerArray) {
                var keyValue = markerArray[index];
                if (keyValue[0] == deviceId) {
                    alertMarkerTemp = keyValue[1];
                }
            }



            //		// Getting Device Data to again display information as Popup
            var deviceData2 = cncDisplay.getDeviceById(deviceId);
            var infowindowData2 = generatePopupHtmlForLeaflet(deviceData2.device);

            alertMarkerTemp.closePopup();
            alertMarkerTemp.bindPopup(infowindowData2); // binding the main information popup
            //
            //		// Add the Off Status Marker
            var greenIcon;
            greenIcon = L.icon({
                iconUrl: sensorStatusOffMarker,
                iconSize: [20, 20]
            });
            alertMarkerTemp.setIcon(greenIcon);

        } catch (exception) {
            console.log("Error processing the received JSON message: " + exception);
        }
    }

    /*******************************************************
     * Function to Turn Off/Disable Device by ID when user clicks on
     * Turn Off button in Popup HTML Box
     *******************************************************/
    function turnOffDeviceByIdFunction(deviceId) {

        var requestUrl = "/cnc/device/updateDeviceStatus";
        var sendData = {
            deviceIdUpdateParam: deviceId
        };
        cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                requestTypeGet, receiveTypeText, function (response, status, error) {
//					displayRequestResult(response, status,
//							resultDiv, deleteOperation);
                });

        // Getting Specific Alert Marker
        var alertMarker;
        for (var index in markerArray) {
            var keyValue = markerArray[index];
            if (keyValue[0] == deviceId) {
                alertMarker = keyValue[1];
            }
        }



        // Getting Device Data to again display information as Popup
        var deviceData2 = cncDisplay.getDeviceById(deviceId);
        var infowindowData2 = generatePopupHtmlForLeaflet(deviceData2.device);

        alertMarker.closePopup();
        alertMarker.bindPopup(infowindowData2); // binding the main information popup

        // Add the Off Status Marker
        var greenIcon;
        greenIcon = L.icon({
            iconUrl: sensorStatusOffMarker,
            iconSize: [20, 20]
        });
        alertMarker.setIcon(greenIcon);


    }

    function isNumber(x) {
        return !isNaN(parseFloat(x));
    }

    /*******************************************************
     * Function to Turn Off/Disable Device by ID when user clicks on
     * Turn Off button in Popup HTML Box
     *******************************************************/
    function updateLocationFunction(deviceId) {

        var logitude = $('#longitudeField').val();
        var latitude = $('#latitudeField').val();

        if (!isNumber(logitude) || !isNumber(latitude)) {
            alert("Entered value of Longitude or Latitude is invalid. Only decimal format is allowed");
        }
        else {
            // Range of Pakistan Extreme Coordinates 
            if (latitude < 22 || latitude > 38 || logitude < 59 || logitude > 78) {
                alert("Entered value of Longitude or Latitude is invalid. Only decimal format is allowed");
            }
            else {

                var requestUrl = "/cnc/device/updateDeviceLocation";
                var sendData = {
                    deviceIdUpdateParam: deviceId,
                    deviceLatitudeUpdateParam: latitude,
                    deviceLongitudeUpdateParam: logitude
                };
                cncDisplay.ExecuteAjaxRequest(requestUrl, sendData,
                        requestTypeGet, receiveTypeText, function (response, status, error) {
//					displayRequestResult(response, status,
//							resultDiv, deleteOperation);
                        });


//                
//
//                // Getting Specific Alert Marker
//                var alertMarker;
//                for (var index in markerArray) {
//                    var keyValue = markerArray[index];
//                    if (keyValue[0] == deviceId) {
//                        alertMarker = keyValue[1];
//                    }
//                }
//
//
//
//                // Getting Device Data to again display information as Popup
//                var deviceData2 = cncDisplay.getDeviceById(deviceId);
//                var infowindowData2 = generatePopupHtmlForLeaflet(deviceData2.device);
//
//                alertMarker.closePopup();
//                alertMarker.bindPopup(infowindowData2); // binding the main information popup
//
//                // Add the Off Status Marker
//                var greenIcon;
//                greenIcon = L.icon({
//                    iconUrl: sensorStatusOffMarker,
//                    iconSize: [20, 20]
//                });
//                alertMarker.setIcon(greenIcon);
            }
        }

    }

    // End private fields and methods

    // Public methods and properties
    return {
        setUserRole: function (role) {
            userRole = role;
        },
        getUserRole: function () {
            return userRole;
        },
        setUsername: function (user) {
            username = user;
        },
        getUsername: function () {
            return username;
        },
        // Method to setup the main C2I Display screen
        initializeMainDisplay: function () {
            setTabs();
            setUserPermissions();
            // setMap();
            setMapLeaflet();
            initStompMessaging();
            setDatePicker();
            cncEvents.setBindings();
        },
        /*
         * Function to execute an Ajax request
         */
        ExecuteAjaxRequest: function (requestUrl, sendData, requestType,
                receiveDataType, callback) {

            $.ajax({
                type: requestType,
                data: sendData,
                dataType: receiveDataType,
                url: requestUrl,
                success: callback,
                error: callback
            });

        },
//        displayInfowindowVideo: function(deviceId, videoStreamLink) {
//            playInfowindowVideo(deviceId, videoStreamLink);
//        },
//        displayTabVideo: function(videoStreamLink) {
//            playTabVideo(videoStreamLink);
//        },
//        closeInfowindow: function(deviceId) {
//            closeAlertInfowindow(deviceId);
//        },
        closeInfowindowForLeaflet: function (deviceId) {
            closeAlertInfowindowForLeaflet(deviceId);
        },
        assignAlert: function () {
            assignAlertFunction();
        },
        runNtvd: function () {
            runNtvdFunction();
        },
        removeDeviceById: function (deviceId) {
            removeDeviceByIdFunction(deviceId);
        },
        authenticatePopup: function (deviceId, devicePhysicalMacAddress, networkId) {
            authenticatePopupFunction(deviceId, devicePhysicalMacAddress, networkId);
        },
        turnOffDeviceById: function (deviceId) {
            turnOffDeviceByIdFunction(deviceId);
        },
        updateLocation: function (deviceId) {
            updateLocationFunction(deviceId);
        },
        /*
         * Function to get a device by it's Id
         */
        getDeviceById: function (deviceId) {

            var result;
            $.ajax({
                type: "get",
                data: {
                    deviceIdSearchParam: deviceId
                },
                url: "/cnc/device/searchById",
                async: false,
                dataType: "json",
                success: function (data, status) {
                    result = data;
                },
                error: function (data, status, er) {
                    console.log("Error: Device Id not found!!!");
                }
            });
            return result;
        },
        cleanupResolvedAlert: function (deviceId) {

            $("#assignedAlertDeviceId option").each(function () {
                if ($(this).val() == deviceId) {
                    $(this).remove();
                }
            });

            var alertInfowindowId = deviceId + "-alertwindow";
            var existingInfowindow = $("#map-div").gmap3({
                get: {
                    id: alertInfowindowId
                }
            });

            if (existingInfowindow) {
                existingInfowindow.close();
            }

            var alertMarker = $("#map-div").gmap3({
                get: {
                    id: deviceId
                }
            });
            if (alertMarker) {
                // Change the animated sensor icon to a static sensor icon
                alertMarker.setIcon(staticSensorIcon);
            }
        }

    }
    // End public fields and methods
}();
