/*
 * Main JavaScript file for C2I display. This JavaScript file is designed 
 * using the Singleton design pattern with public and private properties
 * and methods. The public properties and methods can be accessed from anywhere
 * using the cncDisplay variable
 */

//Singleton
var cncDisplay = function() {

	// Private properties and methods
	var userRole = null;
	var username = null;
	var alertCounter = 0;
	var alertInfowindow = null;
	var messageType = "";
	var $blinker = null;
        var alertDevice = "0C";
	/*
	 * Variable to determine if search is done by Id to determine the result as
	 * a singular value. For searches performed through other filters, there
	 * could be multiple results.
	 */
	var IsSearchById = false;

	var imageDirectoryLocation = "http://" + window.location.host
			+ "/cnc/resources/images/";
	var videoDirectoryLocation = "http://" + window.location.host
			+ "/cnc/resources/videos/";
	var audioDirectoryLocation = "http://" + window.location.host
			+ "/cnc/resources/audio/";
	var javascriptDirectoryLocation = "http://" + window.location.host
			+ "/cnc/resources/js/";
	var batteryInfoPopupText = "<tr><td height='10' width='10'><img src='"
			+ imageDirectoryLocation
			+ "battery_icon.png' height='10' width='20' align='middle'></img></td><td height='10' width='250'> <b>Battery Level:</b> ";
	var linkQualityInfoPopupText = "<tr height='10' width='10'><td><img src='"
			+ imageDirectoryLocation
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

	var animatedSensorIcon = imageDirectoryLocation
			+ "sensor_icon_animated.gif";
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

	/*
	 * Function to initialize the main display tabs and side tabs
	 */
	function setTabs() {
		// Initializing the main display tabs
		$("#main-display-tabs").tabs({
			beforeActivate : function(event, ui) {
				if (ui.newPanel.selector == "#video-tab") {
					$("#videoCameraDropdown").html("");
					cncEvents.setVideoCameraDropdown();
				} else if (ui.newPanel.selector == "#map-tab") {
					//google.maps.event.trigger(map, 'resize');
				} else if (ui.newPanel.selector == "#alerts-tab") {
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
			beforeActivate : function(event, ui) {
				if (ui.newPanel.selector == "#search-device-tab") {
					$("#deviceNetworkSearchDropdown").html("");
				}
				cncEvents.setNetworkDropdown();
			}
		});
		// Initializing the left side vertical alerts tabs
		$("#alerts-side-tabs").tabs().addClass(
				"ui-tabs-vertical ui-helper-clearfix");
		$("#alerts-side-tabs li").removeClass("ui-corner-top").addClass(
				"ui-corner-left");

		// Initializing the left side vertical user tabs
		$("#user-side-tabs").tabs().addClass(
				"ui-tabs-vertical ui-helper-clearfix");
		$("#user-side_-abs li").removeClass("ui-corner-top").addClass(
				"ui-corner-left");
		$("#user-side-tabs").tabs({
			beforeActivate : function(event, ui) {
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
			css_1 : {
				color : '#66CC66',
				backgroundColor : '#F00'
			},
			cycle : 1000,
			ratio : 0.5
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
			$("#device-side-tabs").tabs("option", "disabled", [ 0, 2, 3 ]);
			// Set the active tab in device configuration to
			// device search.
			$("#device-side-tabs").tabs("option", "active", [ 1 ]);
			// Disable the user management tab
			$("#main-display-tabs").tabs("option", "disabled", [ 4 ]);
			// Disable the alerts resolution tab
			$("#alerts-side-tabs").tabs("option", "disabled", [ 1 ]);

		} else if (userRole == 'FIELD_USER') {
			// Disable the add network, delete device and
			// config device tabs under the device
			// configuration tab
			$("#device-side-tabs").tabs("option", "disabled", [ 0, 2, 3 ]);
			// Set the active tab in device configuration to
			// device search.
			$("#device-side-tabs").tabs("option", "active", [ 1 ]);
			// Disable the assigned alerts tab since alerts
			// can only be assigned to Field Users
			$("#alerts-side-tabs").tabs("option", "disabled", [ 0 ]);
			// Set the active tab for alerts to assigned
			// alerts tab for field users
			$("#alerts-side-tabs").tabs("option", "active", 1);
			// Disable the User management and Logs tabs
			$("#main-display-tabs").tabs("option", "disabled", [ 4, 7 ]);
		}

		else if (userRole == 'ADMIN') {
			// Disable the Alerts tab since an admin cannot
			// accept, assign or resolve alerts
			$("#main-display-tabs").tabs("option", "disabled", [ 2 ]);
		}
		
		else if (userRole == 'BATTALION_COMMANDER' || userRole == 'BATTALION_SUPPORT') {
			// Disable the add network, delete device and
			// config device tabs under the device
			// configuration tab
			$("#device-side-tabs").tabs("option", "disabled", [ 0, 2, 3 ]);
			// Set the active tab in device configuration to
			// device search.
			$("#device-side-tabs").tabs("option", "active", [ 1 ]);
			// Disable the alerts and user management tabs
			$("#main-display-tabs").tabs("option", "disabled", [ 2, 4 ]);
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
		//var ws = new SockJS('http://' + window.location.hostname + ':15674/stomp');
		var ws = new SockJS('http://queue-c2i.ugs.sensorflock.com:15674/stomp');

		// Upgrading the protocol from http to STOMP
		var client = Stomp.over(ws);
		client.heartbeat.outgoing = 0;
		client.heartbeat.incoming = 0;
		client.debug = displayMessage('#log-console-div');
		var print_console = displayMessage('#main-console-div');
		var on_connect = function(x) {
			// Subscribing to the auto-delete topic type queue with routing key:
			// browser-clients
			try {
				id = client.subscribe("/topic/browser-clients", function(
						message) {
					var processedMessage = processMessage(message);
					print_console(processedMessage, "to_console");
				});
			} catch (exception) {
				console.log("Error subscribing to queue" + exception);
			}
		};
		var on_error = function(err) {
			console.log('Error: STOMP connection failed');
			client.debug = displayMessage('#log-console-div');
		};
		// Connecting the browser client with login: guest and passwod: guest
		client.connect('guest', 'guest', on_connect, on_error, '/');
	}

	/*
	 * Function to setup the main map display and device selection dropdown
	 */
	function setMap() {

		var mapCenter = new window.google.maps.LatLng(33.5763766667,
				73.0773416667);
		var result;

		// Initializing the map
		map = $("#map-div").gmap3({
			map : {
				options : {
					zoom : 19,
					center : mapCenter,
					mapTypeId : window.google.maps.MapTypeId.SATELLITE
				}
			}
		});
		// Adding an additional property for google maps infowindow for tracking
		// wether a
		// certain infowindow is currently opened or closed
		google.maps.InfoWindow.prototype.opened = false;

		// Getting device information for all the devices stored in the database
		// to plot the device data on the map

		$.ajax({
			type : "get",
			url : "/cnc/device/getAllDevices",
			dataType : "json",
			async : false,
			success : function(data, status) {
				result = data;
			},
			error : function(data, status, er) {
				console.log("Error: Devices could not be mapped!!!");
			}
		});

		$.each(result.devices,
				function(index, value) {

					if (value.deviceLocation != null) {

						var deviceId = value.deviceId;
						var deviceType = value.deviceType;
						var deviceMarkerLocation = [];

						$("#deviceListDropdown").append($("<option>", {
							text : value.deviceId,
							value : value.deviceId
						}));

						try {
							deviceMarkerLocation
									.push(value.deviceLocation.latitude);
							deviceMarkerLocation
									.push(value.deviceLocation.longitude);
						} catch (error) {
							console.log("Error retrieving device location:"
									+ error);
						}

						createAndPlotMarker(deviceId, deviceType,
								deviceMarkerLocation);
					}
				});
	}

	/*
	 * Function to create markers from the device information supplied. This
	 * function also creates info popup html text and sets up trigger functions
	 * on the markers. Also, marker icons are created and plotted on the map by
	 * this function.
	 */
	function createAndPlotMarker(deviceId, deviceType, deviceMarkerLocation) {

		var deviceMarkerIcon;

		try {
			deviceMarkerIcon = generateDeviceMarkerIcon(deviceType);

		} catch (error) {
			console.log("Error creating device marker icon:" + error);
		}

		var infowindowId = deviceId + "-infowindow";
		var circleId = deviceId + "-circle";

		if (deviceType == "SENSOR_NODE") {
			$("#map-div").gmap3({
				circle : {
					id : circleId,
					options : {
						center : deviceMarkerLocation,
						radius : 10,
						fillColor : "#FFAF9F",
						fillOpacity : 0.2,
						strokeColor : "#666666",
						strokeWeight : 2
					}
				}
			});
		}

		/*
		 * Creating markers for every device, setting their icons, info popup
		 * text and marker click and mouse over triggers
		 */
		$("#map-div")
				.gmap3(
						{
							marker : {
								latLng : deviceMarkerLocation,
								id : deviceId,
								options : {
									icon : deviceMarkerIcon,
									optimized : false
								},
								events : {
									/*
									 * mouseover : function(marker, event,
									 * context) { var map =
									 * $(this).gmap3("get"), infowindow = $(
									 * this).gmap3({ get : { id : infowindowId }
									 * }); var deviceData = cncDisplay
									 * .getDeviceById(deviceId); var
									 * InfowindowData =
									 * generatePopupHtml(deviceData.device);
									 * 
									 * if (infowindow) { infowindow.open(map,
									 * marker); infowindow
									 * .setContent(InfowindowData); } else {
									 * $(this) .gmap3( { infowindow : { id :
									 * infowindowId, anchor : marker, options : {
									 * content : InfowindowData, maxWidth : 220 } }
									 * }); } },
									 * 
									 * 
									 * mouseout : function() { var infowindow =
									 * $(this). gmap3({ get : { id :
									 * infowindowId } }); if (infowindow) {
									 * infowindow .close(); } },
									 */

									click : function(marker, event, context) {
										var map = $(this).gmap3("get"), infowindow = $(
												this).gmap3({
											get : {
												id : infowindowId
											}
										});

										var deviceData = cncDisplay
												.getDeviceById(deviceId);
										var InfowindowData = generatePopupHtml(deviceData.device);

										if (infowindow) {
											infowindow.open(map, marker);
											infowindow
													.setContent(InfowindowData);
										} else {
											$(this)
													.gmap3(
															{
																infowindow : {
																	id : infowindowId,
																	anchor : marker,
																	options : {
																		content : InfowindowData,
																		maxWidth : 350
																	}
																}
															});
										}
									},
								}
							}
						});
	}

	/*
	 * Function to generate a marker icon depending on the type of device
	 */
	function generateDeviceMarkerIcon(deviceType) {

		var deviceMarkerIcon;
		if (deviceType == "ROUTER_NODE") {
			deviceMarkerIcon = imageDirectoryLocation
					+ "router_icon_static.png";
		} else if (deviceType == "SENSOR_NODE") {
			deviceMarkerIcon = imageDirectoryLocation
					+ "sensor_icon_static.png";
		} else if (deviceType == "GATEWAY_NODE") {
			deviceMarkerIcon = imageDirectoryLocation
					+ "gateway_icon_static.png";
		} else if (deviceType == "VIDEO_CAMERA_NODE") {
			deviceMarkerIcon = imageDirectoryLocation
					+ "video_camera_icon_static.png";
		} else if (deviceType == "HUMAN_ATTACHED") {
			deviceMarkerIcon = imageDirectoryLocation
					+ "soldier_icon_static.png";
		}
		return deviceMarkerIcon;
	}

	/*
	 * Function to generate html for a device marker popup. An html table is
	 * generated which holds all the device information
	 */
	function generatePopupHtml(value) {

		if (value.deviceSignalStrength == null
				|| value.deviceSignalStrength == "") {
			value.deviceSignalStrength = "N/A";
		} else {
			value.deviceSignalStrength = value.deviceSignalStrength + " dBm";
		}
		if (value.deviceSensitivity == null || value.deviceSensitivity == "") {
			value.deviceSensitivity = "N/A";
		}
		if (value.deviceBattery == null || value.deviceBattery == "") {
			value.deviceBattery = "N/A";
		}
		if (value.deviceLinkQualityIndicator == null
				|| value.deviceLinkQualityIndicator == "") {
			value.deviceLinkQualityIndicator = "N/A";
		}
		var devicePopupHtml = "<div class='infowindow'><font size=2><table><tr><td colspan='2' height='10' width='10'><center><b>"
				+ value.deviceType
				+ "</b></center></td></tr>"
				+ networkIdInfoPopupText
				+ value.networkId
				+ "</td></tr>"
				+ deviceIdInfoPopupText
				+ value.deviceId
				+ "</td></tr>"
				+ deviceStatusInfoPopupText
				+ value.deviceStatus
				+ "</td></tr>"
				+ batteryInfoPopupText
				+ value.deviceBattery
				+ "</td></tr>"
				+ sensitivityInfoPopupText
				+ value.deviceSensitivity
				+ "</td></tr>"
				+ linkQualityInfoPopupText
				+ value.deviceLinkQualityIndicator
				+ "</td></tr>"
				+ signalStrengthInfoPopupText
				+ value.deviceSignalStrength
				+ "</td></tr>"
				+ locationInfoPopupText
				+ value.deviceLocation.latitude
				+ ", "
				+ value.deviceLocation.longitude + "</td></tr>";

		if (value.deviceType == "VIDEO_CAMERA_NODE") {
			devicePopupHtml = devicePopupHtml
					+ "<tr><td colspan='2' height='20'><center><input type='button' id='displayInfowindowVideoButton' name='displayInfowindowVideoButton' value='Play' onclick='cncDisplay.displayInfowindowVideo(&quot;"
					+ value.deviceId
					+ "&quot;,"
					+ "&quot;"
					+ value.videoStreamLink
					+ "&quot;)'/><input type='button' id='displayTabVideoButton' name='displayTabVideoButton' value='Play in Tab' onclick='cncDisplay.displayTabVideo(&quot;"
					+ value.videoStreamLink + "&quot;)'/></center></td></tr>"
		}

		devicePopupHtml = devicePopupHtml + "</table></font><div>"
		return devicePopupHtml;
	}

	/*
	 * Function to play the video stream inside marker infowindow
	 */
	function playInfowindowVideo(deviceId, videoStreamLink) {

		var infowindowVideoHtml = "<div class='infowindow-video'><center>Live stream from "
				+ deviceId + "</center>" + videoHtml + "</div>";
		var infowindowId = deviceId + "-infowindow";
		infowindow = $("#map-div").gmap3({
			get : {
				id : infowindowId
			}
		});
		/*
		 * Cleanup streaming video after infowindow closes since HTML5 does not
		 * provide a stop function or any functionality to cleanly stop the
		 * streaming video. This is implemented to ensure that the streaming
		 * link is stopped and does not keep running and buffering in the
		 * background when the infowindow closes
		 */
		google.maps.event.addListener(infowindow, 'closeclick', function() {
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
			controlType : 'select',
			dateFormat : 'mm-dd-yy',
			timeFormat : 'hh:mm:ss tt'
		});
		$('#endDate').datetimepicker({
			controlType : 'select',
			dateFormat : 'mm-dd-yy',
			timeFormat : 'hh:mm:ss tt'
		});
		var startDateTextBox = $('#startDate');
		var endDateTextBox = $('#endDate');

		/*
		 * Checking for start date greater than end date in which case end date
		 * will be replaced by start date.
		 */
		startDateTextBox
				.datetimepicker({
					onClose : function(dateText, inst) {
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
					onSelect : function(selectedDateTime) {
						endDateTextBox.datetimepicker('option', 'minDate',
								startDateTextBox.datetimepicker('getDate'));
					}
				});
		endDateTextBox
				.datetimepicker({
					onClose : function(dateText, inst) {
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
					onSelect : function(selectedDateTime) {
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

		var latlng = location.split(",");
		try {
			var myLatlng = new google.maps.LatLng(latlng[0], latlng[1]);
		} catch (error) {
			console.log("Error creating google map latlng object: " + error);
		}
		// Get the marker for the device with changed location
		var deviceMarker = $("#map-div").gmap3({
			get : {
				id : deviceId
			}
		});
		// If device marker is already plotted, set the new position of the
		// marker on the map
		if (deviceMarker) {
			// Set the new position for the device
			deviceMarker.setPosition(myLatlng);
			var deviceMarkerCircle = null;
			if (deviceType == "SENSOR_NODE") {
				var circleId = deviceId + "-circle";
				deviceMarkerCircle = $("#map-div").gmap3({
					get : {
						id : circleId
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
		} else {
			// Add the new device to the dropdown
			$("#deviceListDropdown").append($("<option>", {
				text : deviceId,
				value : deviceId
			}));
			// create and plot the marker for the new device
			createAndPlotMarker(deviceId, latlng, deviceType);

		}
	}

	/*
	 * Function to display an alert info window popup on a sensor icon when an
	 * alert message ahs been received from that sensor. This function also
	 * plays an alert sound
	 */
	function generateAlert(deviceId) {

		var deviceMap = $("#map-div").gmap3("get");
		var alert_beep_url = audioDirectoryLocation + "alert_1.wav";
		var alert_beep = new Audio(alert_beep_url);
		var warning_beep_url = audioDirectoryLocation + "warning.wav";
		var warning_beep = new Audio(warning_beep_url);
		
		if (userRole == "ONSITE_USER" || userRole == "COMMANDER" || userRole == "BATTALION_COMMANDER") {
			alertCounter++;
			var alertText = "<div class='infowindow-alert'><center><img src='" + imageDirectoryLocation
					+ "siren.gif' /></center>"
					+ "<center>DEVICE ID: </font>" + deviceId
					+ "<br><center>ALERT#"
					+ alertCounter + "<input type='button' value='Accept' onclick='cncDisplay.closeInfowindow(&quot;"
					+ deviceId + "&quot;)'</center></div>";
		} else if (userRole == "FIELD_USER") {
			var alertText = "<center><img src='"
					+ imageDirectoryLocation
					+ "siren.gif' /></center>"
					+ "<br><b><center>ALERT ASSIGNED<br>TAKE ACTION SOLDIER'!!!</center><b>";
		}
		else if (userRole == "BATTALION_SUPPORT") {
			var alertText = "<div class='infowindow-warning'><center><img src='"
					+ imageDirectoryLocation
					+ "warning.png'/></center>"
					+ "<b><center>WARNING<br>THREAT DETECTED'!!!</center><b></div>";
		}
		var alertMarker = $("#map-div").gmap3({
			get : {
				id : deviceId
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
			get : {
				id : alertInfowindowId
			}
		});
		if (alertInfowindow) {
			if (alertInfowindow.opened == false) {
				alertInfowindow.setContent(alertText);
				alertInfowindow.open(deviceMap, alertMarker);
				alertInfowindow.opened = true;
				if (userRole != "BATTALION_SUPPORT") {
				alert_beep.play();
				}
				else {
					warning_beep.play();
				}
			}
			else {
				alertInfowindow.setContent(alertText);
			}
		} else {
			$("#map-div").gmap3({
				infowindow : {
					id : alertInfowindowId,
					anchor : alertMarker,
					options : {
						content : alertText,
						maxWidth : 200
					},
					events : {
						closeclick : function() {
							var closedInfowindow = $(this).gmap3({
								get : {
									id : alertInfowindowId
								}
							});
							var alertMarker = $(this).gmap3({
								get : {
									id : deviceId
								}
							});
							alertMarker.setIcon(staticSensorIcon);
							closedInfowindow.opened = false;
						}
					}
				}
			});
			
			alertInfowindow = $("#map-div").gmap3({
				get : {
					id : alertInfowindowId
				}
			});
			alertInfowindow.opened = true;
			if (userRole != "BATTALION_SUPPORT") {
				alert_beep.play();
			}
			else {
				warning_beep.play();
			}
		}

		// Auto close the alert popup after the specified time in milliseconds
		// and also change the animated sensor icon back to a static sensor icon
		/*if (userRole == "ONSITE_USER" || userRole == "COMMANDER") {
			setTimeout(function() {
				console.log("closing infowindow");
				alertMarker.setIcon(staticSensorIcon);
				alertInfowindow.close();
				alertInfowindow.opened = false;
			}, 10000);
		}*/
		// Play the alert siren

	}

	function closeAlertInfowindow(deviceId){
		var alertInfowindowId = deviceId + "-alertwindow";
		var infowindow = $("#map-div").gmap3({
			get : {
				id : alertInfowindowId
			}
		});
		google.maps.event.trigger(infowindow, 'closeclick');
		infowindow.close();
	}
	/*
	 * Function to create a Message object from a JSON message
	 */
	function Message(message) {

		for ( var property in message) {
			if (property == "messageDate") {
				this[property] = new Date(message[property]);
			} else {
				this[property] = message[property];
			}
		}
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
		var consoleDisplayMessage = "Console Message";
		var activeTab = $("#main-display-tabs").tabs("option", "active");
		var msg;
		var alert_beep_url = audioDirectoryLocation + "alert_1.wav";
		var alert_beep = new Audio(alert_beep_url);

		try {
			msg = new Message(JSON.parse(message.body));
			messageType = msg.messageType;
			consoleDisplayMessage = msg.messageDate + "> " + msg.messageContent;
			alertDevice = msg.messageDeviceId;

			if (msg.messageType == "ALERT"
					&& msg.messageDeviceType == "SENSOR_NODE") {

				if (userRole == "COMMANDER" || userRole == "ONSITE_USER") {
					// Make the alert tab blink when an alert has been received
					if (activeTab != 2) {
						$blinker.blinker('start');
					}
					//generateAlert(msg.messageDeviceId);
					alert_beep.play();
					var alertDate = new Date(msg.messageDate);
					var alertData = alertDate + " Device Id: "
							+ msg.messageDeviceId;
					$("#receivedAlertDeviceId").append($("<option>", {
						text : alertData,
						value : msg.messageDeviceId
					}));
					$("#receivedAlertDeviceId option").css("background-color",
							"red");
				}
				else if (userRole == "BATTALION_COMMANDER" || userRole == "BATTALION_SUPPORT") {
					//generateAlert(msg.messageDeviceId);
					alert_beep.play();
				}
			} else if (msg.messageType == "LOCATION") {
				if (msg.messageId != null) {
					//changeMarkerLocation(msg.messageDeviceId,
							//msg.messageRawData, msg.messageDeviceType);
				}
			} else if (msg.messageType == "ALERT_ACCEPTED"
					&& (userRole == "COMMANDER" || userRole == "ONSITE_USER")
					&& msg.messageRawData != username) {
				$("#receivedAlertDeviceId option").each(function() {
					if ($(this).val() == msg.messageDeviceId) {
						$(this).remove();
					}
				});
			} else if (msg.messageType == "ALERT_ASSIGNED"
					&& userRole == "FIELD_USER"
					&& msg.messageRawData == username) {
				if (activeTab != 2) {
					$blinker.blinker('start');
				}
				generateAlert(msg.messageDeviceId);
				var dateReceived = new Date(msg.messageDate);
				alertData = dateReceived + " Device Id: " + msg.messageDeviceId;
				$("#assignedAlertDeviceId").append($("<option>", {
					text : alertData,
					value : msg.messageDeviceId
				}));
				$("#assignedAlertDeviceId option").css("background-color",
						"yellow");
			}
		} catch (exception) {
			console.log("Error processing the received JSON message: "
					+ exception);
		}

		return consoleDisplayMessage;
	}

	function displayMessage(console_type) {

		var div = $(console_type + ' div');

		var print = function(message, output) {

			if (messageType == "ALERT" && output == "to_console") {
				if(alertDevice=="0A"){div.append($("<font color='red'>").text(alertDevice).append(": "));}
				else if(alertDevice=="0B"){div.append($("<font color='blue'>").text(alertDevice).append(": "));}
				else if(alertDevice=="0C"){div.append($("<font color='yellow'>").text(alertDevice).append(": "));}
				else if(alertDevice=="0D"){div.append($("<font color='orange'>").text(alertDevice).append(": "));}
				else if(alertDevice=="0F"){div.append($("<font color='green'>").text(alertDevice).append(": "));}
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
				div.append($("<font color='green'>").text(message));
				div.append($("<br>").text(' '));
			}
			div.scrollTop(div.prop("scrollHeight"));
			// $('#div1').scrollTop($('#div1').prop("scrollHeight"));
		}

		return print;
	}
	// End private fields and methods

	// Public methods and properties
	return {
		setUserRole : function(role) {
			userRole = role;
		},
		getUserRole : function() {
			return userRole;
		},
		setUsername : function(user) {
			username = user;
		},
		getUsername : function() {
			return username;
		},
		// Method to setup the main C2I Display screen
		initializeMainDisplay : function() {
			setTabs();
			setUserPermissions();
			//setMap();
			initStompMessaging();
			setDatePicker();
			cncEvents.setBindings();
		},
		/*
		 * Function to execute an Ajax request
		 */
		ExecuteAjaxRequest : function(requestUrl, sendData, requestType,
				receiveDataType, callback) {

			$.ajax({
				type : requestType,
				data : sendData,
				dataType : receiveDataType,
				url : requestUrl,
				success : callback,
				error : callback
			});

		},
		displayInfowindowVideo : function(deviceId, videoStreamLink) {
			playInfowindowVideo(deviceId, videoStreamLink);
		},
		displayTabVideo : function(videoStreamLink) {
			playTabVideo(videoStreamLink);
		},
		closeInfowindow: function(deviceId) {
			closeAlertInfowindow(deviceId);
		},
		/*
		 * Function to get a device by it's Id
		 */
		getDeviceById : function(deviceId) {

			var result;
			$.ajax({
				type : "get",
				data : {
					deviceIdSearchParam : deviceId
				},
				url : "/cnc/device/searchById",
				async : false,
				dataType : "json",
				success : function(data, status) {
					result = data;
				},
				error : function(data, status, er) {
					console.log("Error: Device Id not found!!!");
				}
			});
			return result;
		},
		cleanupResolvedAlert : function(deviceId) {

			$("#assignedAlertDeviceId option").each(function() {
				if ($(this).val() == deviceId) {
					$(this).remove();
				}
			});

			var alertInfowindowId = deviceId + "-alertwindow";
			var existingInfowindow = $("#map-div").gmap3({
				get : {
					id : alertInfowindowId
				}
			});

			if (existingInfowindow) {
				existingInfowindow.close();
			}

			var alertMarker = $("#map-div").gmap3({
				get : {
					id : deviceId
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
