<!-- Main UI display JSP for C2I -->
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<head>
<link rel="stylesheet"
	href='<c:url value="/resources/css/jquery-ui.css" />'>
<link rel="stylesheet"
	href='<c:url value="/resources/css/cnc-main.css" />'>
<script src='<c:url value="/resources/js/jquery-2.0.2.min.js" />'></script>
<script src='<c:url value="/resources/js/jquery-ui.min.js" />'></script>
<script
	src='<c:url value="/resources/js/jquery-ui-timepicker-addon.js" />'></script>
<script
	src='<c:url value="/resources/js/jquery-ui-tab-blinker-addon.js" />'></script>
<!--<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=true"></script>-->
<script type="text/javascript"
	src='<c:url value="/resources/js/gmap3.min.js" />'></script>
<script src='<c:url value="/resources/js/sockjs-0.3.min.js" />'></script>
<script src='<c:url value="/resources/js/stomp.js" />'></script>
<script src='<c:url value="/resources/js/display_main_offline.js" />'></script>
<script src='<c:url value="/resources/js/display_events_offline.js" />'></script>

</head>

<body>
	<!-- Tabs at the top of Command and control system -->
	<div id="main-display-tabs" class="ui-tabs">

		<ul class="ui-tabs-nav">
			<li><a href="#map-tab"><span>Field Network Map</span></a></li>
			<li><a href="#console-tab"><span>Console</span></a></li>
			<li><a href="#alerts-tab" class="blinker"><span>Alerts</span></a></li>
			<li><a href="#message-tab"><span>Message Search</span></a></li>
			<li><a href="#user-tab"><span>User Administration</span></a></li>
			<li><a href="#device-tab"><span>Device Configuration</span></a></li>
			<li><a href="#video-tab"><span>Video Streaming</span></a></li>
			<li><a href="#logs-tab"><span>Logs</span></a></li>
		</ul>

		<div id="map-tab">
			<div id="container">
				<div id="map-div"></div>
				<!-- Div at the right -->
				<div id="device-list">
					<center>
						<label for="deviceListDropdown">Device List</label> <br> <br>
					</center>
					<select name="deviceListDropdown" id="deviceListDropdown">
						<option value="" style="display: none">Select a device</option>
					</select>
				</div>
			</div>
		</div>

		<div id="console-tab">
			<div id="main-console-div" class="console">
				<div id="main-console-message-div"></div>
			</div>
		</div>

		<div id="alerts-tab">

			<div id="alerts-side-tabs">
				<ul>
					<li><a href="#alerts-action-tab"><span>
								Accept/Assign Alerts</span></a></li>
					<li><a href="#alerts-assigned-tab"><span>Assigned
								Alerts</span></a></li>
				</ul>

				<div id="alerts-action-tab">

					<div id="alert-action-div" class="box alertbox">
						<br>
						<form id="alertForm" name="alertForm" class="alerts">
							<label for="receivedAlertDeviceId">Alerts Received</label><br>
							<select id="receivedAlertDeviceId" name="receivedAlertDeviceId"
								size="6" value="">
							</select><br> <label for="assignedTo">Assign a Field user</label><br>
							<select id="assignedTo" name="assignedTo">
								<option value="">Select a user</option>
							</select><input type="hidden" id="assignedBy" name="assignedBy" /> <br>
							<label for="assignComments">Comments</label><br>
							<textarea rows="3" cols="40" id="assignComments"
								name="assignComments" value=""></textarea>
							<br> <br>
							<center>
								<input type="button" id="acceptAlertButton"
									name="acceptAlertButton" value="Accept" /> <input
									type="button" id="assignAlertButton" name="assignAlertButton"
									value="Assign" />
							</center>
						</form>
					</div>
					<div id="alert-window-result-div" class="result"></div>

				</div>

				<div id="alerts-assigned-tab">
					<div id="alert-assigned-div" class="box alertbox">
						<br>
						<form id="alertResolvedForm" name="alertReolvedForm"
							class="alerts">
							<label for="assignedAlertDeviceId">Alerts Assigned</label><br>
							<select id="assignedAlertDeviceId" name="assignedAlertDeviceId"
								size="4" value="">
							</select><br> <label for="resolveComments">Comments</label><br>
							<textarea rows="3" cols="40" id="resolveComments"
								name="resolveComments" value=""></textarea>
							<input type="hidden" id="resolvedBy" name="resolvedBy" /> <br>
							<br>
							<center>
								<input type="button" id="resolveAlertButton"
									name="resolveAlertButton" value="Resolve" />
							</center>
						</form>
					</div>
					<div id="alert-resolve-result-div" class="result"></div>
				</div>
			</div>
		</div>

		<div id="device-tab">
			<div id="device-side-tabs">
				<ul>
					<li><a href="#add-network-tab"><span>Add Network</span></a></li>
					<li><a href="#search-device-tab"><span>Search
								Device</span></a></li>
					<li><a href="#delete-device-tab"><span>Delete
								Device</span></a></li>
					<li><a href="#config-device-tab"><span>Config
								Device</span></a></li>
				</ul>

				<div id="add-network-tab">
					<div id="create-network-div" class="box">
						<br> <br>
						<form id="networkAddForm" method="post" class="forms">
							<label for="networkId">Network Id
								&nbsp;&nbsp;&nbsp;&nbsp;</label> <input type="text" id="networkId"
								name="networkId" value="" /><br> <label for="networkName">Network
								Name </label> <input type="text" id="networkName" name="networkName"
								value="" /><br> <label for="networkType">Network
								Type &nbsp;&nbsp;</label><select name="networkType" id="networkType">
								<option value="SENSOR_NETWORK">SENSOR NETWORK</option>
								<option value="CAMERA_NETWORK">CAMERA NETWORK</option>
							</select><br> <label for="networkStatus">Network Status </label> <select
								id="networkStatus" name="networkStatus" id="userStatus">
								<option value="ACTIVE">UP</option>
								<option value="DEACTIVATED">DOWN</option>
							</select>
							<center>
								<input type="button" id="saveNetworkButton" value="Save Network" />
							</center>
						</form>
					</div>
					<div id="create-network-result-div" class="result"></div>
				</div>
				<div id="search-device-tab" class="forms">
					<div id="device-search-div" class="search">
						<br> <br> <label for="deviceIdSearchTextField">Device
							Id </label><input type="text" id="deviceIdSearchTextField"
							name="deviceIdSearchTextField" value="" /> <input type="button"
							id="searchDeviceByIdButton" value="Search By Id" /><br> <label
							for="deviceNetworkSearchDropdown">Network
							&nbsp;&nbsp;&nbsp;</label> <select name="deviceNetworkSearchDropdown"
							id="deviceNetworkSearchDropdown">
						</select> <input type="button" id="searchDevicesByNetworkButton"
							value="Search By Network" /><br> <label
							for="deviceTypeSearchDropdown">Type
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <select
							name="deviceTypeSearchDropdown" id="deviceTypeSearchDropdown">
							<option value="SENSOR_NODE">SENSOR NODE</option>
							<option value="GATEWAY_NODE">GATEWAY NODE</option>
							<option value="ROUTER_NODE">ROUTER NODE</option>
							<option value="VIDEO_CAMERA_NODE">VIDEO CAMERA NODE</option>
						</select> <input type="button" id="searchDevicesByTypeButton"
							value="Search By Type" /><br> <label
							for="deviceStatusSearchDropdown">Status
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <select
							name="deviceStatusSearchDropdown" id="deviceStatusSearchDropdown">
							<option value="ON">ON</option>
							<option value="OFF">OFF</option>
							<option value="DEBUG">DEBUG</option>
							<option value="SLEEP">SLEEP</option>
						</select> <input type="button" id="searchDevicesByStatusButton"
							value="Search By Status" />
					</div>
					<div id="search-device-result-div" class="result"></div>
				</div>
				<div id="delete-device-tab" class="forms">
					<div id=device-delete-div " class="box">
						<br> <br> <label for="deviceIdDeleteTextField">Device
						</label><input type="text" id="deviceIdDeleteTextField"
							name="deviceIdDeleteTextField" value="" style="width: 140px" />
						<input type="button" id="deleteDeviceButton" value="Delete"
							style="width: 80px" />
					</div>
					<div id="delete-device-result-div" class="result"></div>
				</div>
				<div id="config-device-tab" class="forms">
					<div id="device-config-div" class="box">
						<br> <br> <label for="deviceIdConfigTextField">Device
						</label><input type="text" id="deviceIdConfigTextField"
							name="deviceIdConfigTextField" value="" style="width: 140px" />
						<input type="button" id="configDeviceButton" value="Search"
							style="width: 80px" />
					</div>
					<div id="config-device-result-div" class="result"></div>
				</div>
			</div>
		</div>

		<div id="user-tab">
			<div id="user-side-tabs">
				<ul>
					<li><a href="#create-user-tab"><span>Add User</span></a></li>
					<li><a href="#update-user-tab"><span>Update User</span></a></li>
					<li><a href="#search-user-tab"><span>Search User</span></a></li>
					<li><a href="#delete-user-tab"><span>Delete User</span></a></li>
				</ul>

				<div id="create-user-tab">
					<div id="create-user-div" class="box">
						<br> <br>
						<form id="userAddForm" method="post" class="forms">
							<label for="userId">User Name</label> <input type="text"
								id="userId" name="userId" value="" /><br> <label
								for="firstName">First Name </label> <input type="text"
								id="firstName" name="firstName" value="" /><br> <label
								for="lastName">Last Name </label><input type="text"
								id="lastName" name="lastName" value="" /><br> <label
								for="password">Password&nbsp;&nbsp;&nbsp;</label><input
								type="text" id="password" name="password" value="" /><br>
							<label for="userRole">Role&nbsp;&nbsp;&nbsp;&nbsp;</label><select
								name="userRole" id="userRole">
								<option value="FIELD_USER">FIELD_USER</option>
								<option value="BATTALION_COMMANDER">BATTALION_COMMANDER</option>
								<option value="BATTALION_SUPPORT">BATTALION_SUPPORT</option>
								<option value="SITE_USER">ONSITE_USER</option>
								<option value="COMMANDER">COMMANDER</option>
								<option value="ADMIN">ADMIN</option>
							</select><br> <label for="userStatus">Status</label> <select
								id="userStatus" name="userStatus">
								<option value="ACTIVE">ACTIVE</option>
								<option value="DEACTIVATED">DEACTIVATED</option>
							</select>
							<center>
								<input type="button" id="saveUserButton" value="Save User" />
							</center>
						</form>
					</div>
					<div id="create-user-result-div" class="result"></div>
				</div>
				<div id="search-user-tab" class="forms">
					<div id="user-search-div" class="search">
						<br> <br> <label for="userIdSearchTextField">User
							Id </label><input type="text" id="userIdSearchTextField"
							name="userIdSearchTextField" value="" /> <input type="button"
							id="searchUserByIdButton" value="Search By Id" /><br> <label
							for="userNameSearchTextField">First or Last Name </label><input
							type="text" id="userNameSearchTextField"
							name="userNameSearchTextField" value="" /> <input type="button"
							id="searchUsersByNameButton" value="Search By Name" /><br>
						<label for="userRoleSearchDropdown">Role </label> <select
							name="userRoleSearchDropdown" id="userRoleSearchDropdown">
							<option value="FIELD_USER">FIELD_USER</option>
							<option value="BATTALION_COMMANDER">BATTALION_COMMANDER</option>
							<option value="BATTALION_SUPPORT">BATTALION_SUPPORT</option>
							<option value="SITE_USER">ONSITE_USER</option>
							<option value="COMMANDER">COMMANDER</option>
							<option value="ADMIN">ADMIN</option>
						</select> <input type="button" id="searchUsersByRoleButton"
							value="Search By Role" /><br> <label
							for="userStatusSearchDropdown">Status </label> <select
							name="userStatusSearchDropdown" id="userStatusSearchDropdown">
							<option value="ACTIVE">ACTIVE</option>
							<option value="DEACTIVATED">DEACTIVATED</option>
						</select> <input type="button" id="searchUsersByStatusButton"
							value="Search By Status" />
					</div>
					<div id="search-user-result-div" class="result"></div>
				</div>
				<div id="update-user-tab">
					<div id="update-user-div" class="box">
						<br> <br>
						<form id="updateUserForm" method="post" class="forms">
							<label for="updateUserId">User Name</label> <select
								id="updateUserId" name="updateUserId">
								<option value="" style="display: none">Select a
									username</option>
							</select><br> <label for="updateFirstName">First Name </label><input
								type="text" name="updateFirstName" id="updateFirstName" value="" /><br>
							<label for="updateLastName">Last Name </label> <input type="text"
								id="updateLastName" name="updateLastName" value="" /><br>
							<label for="updatePassword">Password&nbsp;&nbsp;</label> <input
								type="text" id="updatePassword" name="updatePassword" value="" /><br>
							<label for="updateUserRole">Role&nbsp;&nbsp;&nbsp;</label> <select
								name="updateUserRole" id="updateUserRole">
								<option value="FIELD_USER">FIELD_USER</option>
								<option value="BATTALION_COMMANDER">BATTALION_COMMANDER</option>
								<option value="BATTALION_SUPPORT">BATTALION_SUPPORT</option>
								<option value="ONSITE_USER">ONSITE_USER</option>
								<option value="COMMANDER">COMMANDER</option>
								<option value="ADMIN">ADMIN</option>
							</select><br> <label for="updateUserStatus">Status</label> <select
								name="updateUserStatus" id="updateUserStatus">
								<option value="ACTIVE">ACTIVE</option>
								<option value="DEACTIVATED">DEACTIVATED</option>
							</select>
							<center>
								<input type="button" id="updateUserButton" value="Update User" />
							</center>
						</form>
					</div>
					<div id="update-user-result-div" class="result"></div>
				</div>
				<div id="delete-user-tab" class="forms">

					<div id="delete-user-div" class="box">
						<br> <br> <label for="deleteUserIdDropdown">User
							Id</label> <select name="deleteUserIdDropdown" id="deleteUserIdDropdown">
							<option value="" style="display: none">Select a username</option>
						</select> <input type="button" id="deleteUserButton" value="Delete"
							style="width: 80px" />
					</div>
					<div id="delete-user-result-div" class="result"></div>
				</div>
			</div>
		</div>

		<div id="message-tab">
			<div id="message-search-container" class="search search-message">
				<div id="datepicker" class="forms">
					<form id="messageSearchByDateForm" method="get">
						<br> <br> <label for="messageType">Message Type
						</label><select name="messageType" id="messageType">
							<!-- <option value="ALL">ALL TYPES</option>-->
							<option value="ALERT">ALERT</option>
							<option value="STATUS">STATUS</option>
							<option value="BATTERY">BATTERY</option>
							<option value="LOCATION">LOCATION</option>
							<option value="LINK_QUALITY">LINK_QUALITY</option>
							<option value="SIGNAL_STRENGTH">SIGNAL STRENGTH</option>
							<option value="SENSITIVITY">SENSITIVITY</option>
						</select><br> <label for="startDate">Start Date</label> <input
							type="text" name="startDate" id="startDate" value="" /> <label
							for="endDate">End Date</label> <input type="text" name="endDate"
							id="endDate" value="" /> <input type="button"
							id="searchMessagesByTypeAndDateButton" value="Search by date" />
					</form>
				</div>
				<div class="forms">
					<br> <label for="messageSearchByIdTextField">Device Id</label>
					<input type="text" id="messageSearchByIdTextField"
						name="messageSearchByIdTextField" value=""> <input
						type="button" id="searchMessagesByDeviceIdButton"
						value="Search by Id" />
				</div>
			</div>
			<div id="search-message-result-div" class="result"></div>
		</div>

		<div id="video-tab">
			<div id="video-container">
				<div id="video-div">
					<video id="tab-video-player" height="500" width="800"
						poster="https://sensorflock.recruiterbox.com/static/images/company_logos/SensorFlock-Logo-NG-Trans-opt.gif"
						controls autoplay>
						<source src="" />
						<p class="warning">Your browser does not support HTML5 video.</p>
					</video>
				</div>
				<div id="video-camera-list">
					<center>
						<label for="videoCameraDropdown">Video Camera List</label>
					</center>
					<br> <select name="videoCameraDropdown"
						id="videoCameraDropdown">
						<!--  <option value="" style="display: none">Select a Video
							Camera</option>-->
					</select>
				</div>
			</div>
		</div>

		<div id="logs-tab">
			<div id="log-console-div" class="console">
				<div id="log-console-message-div"></div>
			</div>
		</div>

	</div>

	<script type="text/javascript">
		cncDisplay.setUserRole("${userRole}");
		cncDisplay.setUsername("${username}");
		cncDisplay.initializeMainDisplay();
	</script>

</body>
</html>
