<!-- Main UI display JSP for C2I -->
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<head>

    <link rel="stylesheet" href='<c:url value="/resources/css/leaflet.css" />'>
    <link rel="stylesheet" href='<c:url value="/resources/css/leaflet.label.css" />'>


    <script	src='<c:url value="/resources/js/leaflet.js" />'></script>
    <script	src='<c:url value="/resources/js/leaflet.label.js" />'></script>


    <link rel="stylesheet" href='<c:url value="/resources/css/jquery-ui.css" />'>
    <link rel="stylesheet" href='<c:url value="/resources/css/cnc-main.css" />'>
    <script src='<c:url value="/resources/js/jquery-2.0.2.min.js" />'></script>
    <script src='<c:url value="/resources/js/jquery-ui.min.js" />'></script>
    <script	src='<c:url value="/resources/js/jquery-ui-timepicker-addon.js" />'></script>
    <script	src='<c:url value="/resources/js/jquery-ui-tab-blinker-addon.js" />'></script>

    <!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script> -->
    <%-- <script type="text/javascript" src='<c:url value="/resources/js/gmap3.min.js" />'></script> --%>

    <script src='<c:url value="/resources/js/sockjs-0.3.min.js" />'></script>
    <script src='<c:url value="/resources/js/stomp.js" />'></script>
    <script src='<c:url value="/resources/js/display_main.js" />'></script>
    <script src='<c:url value="/resources/js/display_events.js" />'></script>

</head>

<body>
    <table width="100%" border="0" style="background-color: silver;"   cellspacing="0" cellpadding="0"  >
        <tr  style="background: #6965C6; color:white;" > 
            <td width="10%">
                <img src="<%=request.getContextPath()%>/resources/images/pak_army_logo.png" height="90" width="100" alt="Pak Army" >
            </td>
            <td width="85%" height="100%" >
                <table width="100%">
                    <tr valign="center"><td><font size="6.5" style="font-weight: bold; font-family: ariel;" >Unattended Ground Sensor</font></td></tr>
                    <tr valign="top"><td>&nbsp;<font style="font-weight: bold; font-family: ariel;" >Command and Control System</font></td></tr>
                </table>

            </td>

            <!--            <td width="35%" height="100%">
                            <table>
                                <tr>
                                    <td>Latitude : <input type="text" id="c2iLatitude" name="c2iLatitude" value="" /></td>
                                    <td>Longitude : <input type="text" id="c2iLongitude" name="c2iLongitude" value="" /></td>
                                </tr>
                                <tr>
                                    <td colspan="2" width="100%" align="right"><input width="100%" type="button" id="updateC2ILocation" name="updateC2ILocation" value="Update" onclick="cncDisplay.assignAlert();" /></td>
                                </tr>
                            </table>
                        </td>-->
            <td width="25%" align="center">
                <table width="100%"  >
                    <tr>
                        <td colspan="2" align="center">
                            <font id="lastAlertTime" ></font>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table width="100%" frame="box" style="border-color:red;" >
                                <tr><td align="center">Last<font color="#6965C6">_</font>alert<font color="#6965C6">_</font>Device<font color="#6965C6">_</font>ID</td></tr>
                                <tr><td align="center">
                                        <font size="6.5" id="lastAlertDeviceId" style="font-weight: bold; font-family: ariel;" class="blinker">None</font>&nbsp;

                                    </td></tr>

                            </table>

                        </td>
                        <td>
                            <table width="100%" frame="box" style="border-color:red;" >
                                <tr><td align="center">Total<font color="#6965C6">_</font>no<font color="#6965C6">_</font>of<font color="#6965C6">_</font>Alerts</td></tr>
                                <tr><td align="center">
                                        <font size="6.5" id="noOfAlertsPending" style="font-weight: bold; font-family: ariel;" class="blinker">0</font>&nbsp;

                                    </td></tr>
                            </table>

                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <font id="lastAlertLatLong" ></font>
                        </td>

                    </tr>
                </table>





            </td>





        </tr>
        <tr><td colspan="4">
                <!-- Tabs at the top of Command and control system -->
                <div id="main-display-tabs" class="ui-tabs">

                    <ul class="ui-tabs-nav">
                        <!-- 			<li><a href="#map-tab"><span>Field Network Map</span></a></li> -->
                        <li><a href="#map_tab_leaflet" style="outline: 0;"><span>Sensors Map</span></a></li>
                        <li><a href="#console-tab" style="outline: 0;" class="blinker"><span>Alerts</span></a></li>
                        <!--<li><a href="#alerts-tab" class="blinker" style="outline: 0;"><span>Alerts</span></a></li>-->
                        <li><a href="#message-tab" style="outline: 0;"><span>Message Search</span></a></li>
                        <!--<li><a href="#user-tab" style="outline: 0;"><span>User Administration</span></a></li>-->
                        <li><a href="#device-tab" style="outline: 0;"><span>Device Configuration</span></a></li>
                        <!-- <li><a href="#video-tab" style="outline: 0;"><span>Video Streaming</span></a></li> -->
                        <li><a href="#logs-tab" style="outline: 0;"><span>Logs</span></a></li>

                    </ul>

                    <!-- 		<div id="map-tab"> -->
                    <!-- 			<div id="container"> -->
                    <!-- 				<div id="map-div"></div> -->
                    <!-- 				<div id="device-list"> -->
                    <!-- 					<select name="deviceListDropdown" id="deviceListDropdown" class="devicelist"> -->
                    <!-- 						<option value="" style="display: none">Select a device</option> -->
                    <!-- 					</select> -->
                    <!-- 				</div> -->
                    <!-- 			</div> -->
                    <!-- 		</div> -->


                    <div id="map_tab_leaflet">
                        <div id="container-leaflet">
                            <div style="height: 600px" id="map"></div>


                            <div id="device-list" style="z-index: 10000 !important;">
                                <select name="deviceListDropdown2" id="deviceListDropdown2"
                                        class="devicelist" >
                                    <option value="">Select a device</option>
                                </select>
                            </div>

                            <!-- 				<div id="alert_info_div" > -->
                            <!-- 					<font id="alertInfoText" style="color: white;" ></font> -->
                            <!-- 				</div> -->

                            <div id="alert_type_info_div" >
                                <font id="alertTypeInfoDiv" style="color: white;" ></font>
                            </div>


                        </div>

                    </div>	

                    <div id="console-tab">
                        <div id="main-console-div" class="console">
                            <div id="main-console-message-div" style="overflow-y:scroll;"></div>
                        </div>

                        <!--This div is only to show number of alerts and last alert device ID at top-->
                        <div style="visibility: hidden">
                            <form id="alertForm" name="alertForm" class="alerts" >
                                <select id="receivedAlertDeviceId" name="receivedAlertDeviceId" size="10"> </select>
                            </form>
                        </div>
                    </div>

                    <!--                    <div id="alerts-tab">
                    
                    
                                            <div id="alerts-side-tabs">
                                                <ul>
                                                    <li><a href="#alerts-action-tab" style="outline: 0;"><span>
                                                                Accept/Assign Alerts</span></a></li>
                                                    <li><a href="#alerts-assigned-tab" style="outline: 0;"><span>Assigned
                                                                Alerts</span></a></li>
                                                </ul>
                    
                                                <div id="alerts-action-tab">
                    
                    
                                                    <div id="alert-action-div" class="box alertbox">
                                                        <h3> Accept/Assign Alerts </h3>
                                                        <form id="alertForm" name="alertForm" class="alerts" >
                                                            <label for="receivedAlertDeviceId">Alerts Received</label><br>
                                                            <select id="receivedAlertDeviceId" name="receivedAlertDeviceId"
                                                                    size="10" value="">
                                                            </select><br> <label for="assignedTo">Assign a Field user</label><br>
                                                            <select id="assignedTo" name="assignedTo" >
                                                                <option value="" >Select a user</option>
                                                            </select><input type="hidden" id="assignedBy" name="assignedBy" /> <br>
                                                            <label for="assignComments">Comments</label><br>
                                                            <textarea rows="3" cols="68" id="assignComments"
                                                                      name="assignComments" value=""></textarea>
                                                            <br> <br>
                                                            <center>
                                                                                                                            <input type="button" id="acceptAlertButton"	name="acceptAlertButton" value="Accept" />  
                                                                <input	type="button" id="assignAlertButton" name="assignAlertButton"
                                                                       value="Assign" onclick="cncDisplay.assignAlert();" />
                                                            </center>
                                                        </form>
                                                    </div>
                                                    <div id="alert-window-result-div" class="result"></div>
                    
                                                </div>
                    
                                                <div id="alerts-assigned-tab">
                                                    <div id="alert-assigned-div" class="box alertbox">
                                                        <h3> Assigned Alerts </h3>
                                                        <form id="alertResolvedForm" name="alertReolvedForm"
                                                              class="alerts">
                                                            <label for="assignedAlertDeviceId">Alerts Assigned</label><br>
                                                            <select id="assignedAlertDeviceId" name="assignedAlertDeviceId"
                                                                    size="4" value="">
                                                            </select><br> <label for="resolveComments">Comments</label><br>
                                                            <textarea rows="3" cols="68" id="resolveComments"
                                                                      name="resolveComments" value="" ></textarea>
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
                                        </div>-->

                    <div id="device-tab">
                        <div id="device-side-tabs">
                            <ul>
                                <!--<li><a href="#add-network-tab" style="outline: 0;"><span>Add Network</span></a></li>-->
                                <li><a href="#add-device-tab" style="outline: 0;"><span>Add Device</span></a></li>
                                <li><a href="#search-device-tab" style="outline: 0;"><span>Search Device</span></a></li>
                                <li><a href="#delete-device-tab" style="outline: 0;"><span>Delete
                                            Device</span></a></li>
                                <!--<li><a href="#config-device-tab" style="outline: 0;"><span>Config Device</span></a></li>-->
                            </ul>

                            <!--                            <div id="add-network-tab">
                                                            <div id="create-network-div" class="box">
                                                                <br> <br>
                                                                <form id="networkAddForm" method="post" class="forms">
                                                                    <label for="networkId">Network Id &nbsp;&nbsp;&nbsp;&nbsp;</label> 
                                                                    <input type="text" id="networkId" name="networkId" value="" /><br> 
                                                                    <label for="networkName">Network Name </label> 
                                                                    <input type="text" id="networkName" name="networkName" value="" /><br> 
                                                                    <label for="networkType">Network Type &nbsp;&nbsp;</label>
                                                                    <select name="networkType" id="networkType"> 
                                                                        <option value="SENSOR_NETWORK">SENSOR NETWORK</option>
                                                                        <option value="CAMERA_NETWORK">CAMERA NETWORK</option>
                                                                    </select><br> 
                                                                    <label for="networkStatus">Network Status </label> 
                                                                    <select id="networkStatus" name="networkStatus" id="userStatus">
                                                                        <option value="ACTIVE">UP</option>
                                                                        <option value="DEACTIVATED">DOWN</option>
                                                                    </select>
                                                                    <center>
                                                                        <input type="button" id="saveNetworkButton" value="Save Network" />
                                                                    </center>
                                                                </form>
                                                            </div>
                                                            <div id="create-network-result-div" class="result"></div>
                                                        </div>-->


                            <div id="add-device-tab" class="forms">
                                <div id="create-device-div" class="search">
                                    <br> <br>
                                    <form id="deviceAddForm" method="post" >
                                        <label for="deviceId">Device Id &nbsp;&nbsp;&nbsp;&nbsp;</label> 
                                        <input type="text" id="deviceId" name="deviceId" value="" /><br> 

                                        <label for="deviceType">Device Type&nbsp;</label> 
                                        <select name="deviceType" id="deviceType"> 
                                            <option value="PIR_SENSOR_NODE">PIR sensor</option>
                                            <option value="DUAL_SENSOR_NODE">MW sensor</option>
                                            <option value="SEISMIC_SENSOR_NODE">Seismic sensor</option>
                                            <option value="ROUTER_NODE">Router</option>
                                            <option value="C2I_SYSTEM">C2I system</option>

                                        </select><br> 
                                        <center>
                                            <input type="button" id="saveDeviceButton" value="Save Device" />
                                        </center>
                                    </form>
                                </div>
                                <div id="create-device-result-div" class="result"></div>
                            </div>

                            <div id="search-device-tab" class="forms">

                                <div id="device-search-div" class="search">
                                    <br> <br> <label for="deviceIdSearchTextField">Device Id &nbsp;&nbsp;&nbsp;</label><input type="text" id="deviceIdSearchTextField"
                                                      name="deviceIdSearchTextField" value="" /> <input type="button"
                                                      id="searchDeviceByIdButton" value="Search By Id" style="width: 200px;" /><br> <label
                                                      for="deviceNetworkSearchDropdown">Network
                                        &nbsp;&nbsp;&nbsp;</label> 
                                    <select name="deviceNetworkSearchDropdown" id="deviceNetworkSearchDropdown">
                                    </select> 
                                    <input type="button" id="searchDevicesByNetworkButton" style="width: 200px;"
                                           value="Search By Network" /><br> <label
                                           for="deviceTypeSearchDropdown">Type
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <select
                                        name="deviceTypeSearchDropdown" id="deviceTypeSearchDropdown">
                                        <option value=""></option>
                                        <option value="PIR_SENSOR_NODE">PIR sensor node</option>
                                        <option value="DUAL_SENSOR_NODE">Microwave sensor node</option>
                                        <option value="SEISMIC_SENSOR_NODE">Seismic sensor node</option>
                                        <option value="ROUTER_NODE">Router node</option>

                                    </select> <input type="button" id="searchDevicesByTypeButton"
                                                     value="Search By Type" style="width: 200px;" /><br> <label
                                                     for="deviceStatusSearchDropdown">Status
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <select
                                        name="deviceStatusSearchDropdown" id="deviceStatusSearchDropdown">
                                        <option value=""></option>
                                        <option value="ON">On</option>
                                        <option value="OFF">Off</option>
                                        <option value="SLEEP">Sleep</option>
                                    </select> <input type="button" id="searchDevicesByStatusButton" style="width: 200px;" value="Search By Status" />
                                </div>
                                <div id="search-device-result-div" class="result"></div>
                            </div>
                            <div id="delete-device-tab" class="forms">
                                <div id="device-delete-div" class="search">
                                    <br> <br> <label for="deviceIdDeleteTextField">Device Id &nbsp;&nbsp;</label>
                                    <input type="text" id="deviceIdDeleteTextField" name="deviceIdDeleteTextField" value="" />
                                    <input type="button" id="deleteDeviceButton" value="Delete"
                                           style="width: 80px" />
                                </div>
                                <div id="delete-device-result-div" class="result"></div>
                            </div>
                            <!--                            <div id="config-device-tab" class="forms">
                                                            <div id="device-config-div" class="box">
                                                                <br> <br> <label for="deviceIdConfigTextField">Device
                                                                </label><input type="text" id="deviceIdConfigTextField"
                                                                               name="deviceIdConfigTextField" value="" style="width: 140px" />
                                                                <input type="button" id="configDeviceButton" value="Search"
                                                                       style="width: 80px" />
                                                            </div>
                                                            <div id="config-device-result-div" class="result"></div>
                                                        </div>-->
                        </div>
                    </div>

                    <!--                    <div id="user-tab">
                                            <div id="user-side-tabs">
                                                <ul>
                                                    <li><a href="#create-user-tab" style="outline: 0;"><span>Add User</span></a></li>
                                                    <li><a href="#update-user-tab" style="outline: 0;"><span>Update User</span></a></li>
                                                    <li><a href="#search-user-tab" style="outline: 0;"><span>Search User</span></a></li>
                                                    <li><a href="#delete-user-tab" style="outline: 0;"><span>Delete User</span></a></li>
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
                                        </div>-->











                    <div id="message-tab">
                        <!-- 						<div id="message-side-tabs"> -->
                        <!-- 							<ul> -->
                        <!-- 								<li><a href="#search-message-tab" style="outline: 0;"><span>Search Message</span></a></li> -->

                        <!-- 							</ul> -->
                        <div id="search-message-tab">
                            <div id="message-search-container" class="search-message">
                                <div id="datepicker" class="forms">
                                    <form id="messageSearchByDateForm" method="get">
                                        <br>
                                        <label for="messageType">Message Type </label>
                                        <select name="messageType" id="messageType">
                                            <!-- <option value="ALL">ALL TYPES</option>-->
                                            <option value="ALERT">ALERT</option>
                                            <option value="STATUS">STATUS</option>
                                            <option value="BATTERY">BATTERY</option>
                                            <option value="LOCATION">LOCATION</option>
                                        </select>
                                        <label for="startDate">Start Date</label> 
                                        <input type="text" name="startDate" id="startDate" value="" /> 
                                        <label for="endDate">End Date</label> 
                                        <input type="text" name="endDate" id="endDate" value="" /> 
                                        <input type="button" style="width: 220px" id="searchMessagesByTypeAndDateButton" value="Search by type and date" />
                                    </form>
                                </div>
                                <div class="forms">
                                    <br> <label for="messageSearchByIdTextField">Device Id&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> 
                                    <input type="text" id="messageSearchByIdTextField"
                                                      name="messageSearchByIdTextField" value=""> <input
                                                      type="button" id="searchMessagesByDeviceIdButton"
                                                      value="Search by Id" />
                                </div>
                            </div>
                            <div id="search-message-result-div" class="result-message"></div>
                        </div>
                        
                        <!-- 						</div> -->
                    </div>

                    <!-- 					<div id="video-tab"> -->
                    <!-- 			<div id="video-container"> -->
                    <!-- 				<div id="video-camera-list"> -->
                    <%-- 						<center> --%>
                    <!-- 							<h3>Video Camera List</h3> -->
                    <%-- 						</center> --%>

                    <!-- 						<div align="center">  -->
                    <!-- 						<select name="videoCameraDropdown" -->
                    <!-- 							id="videoCameraDropdown" class="devicelist"> -->
                    <!-- 							 <option value="" style="display: none">Select a Video
                    <!-- 								Camera</option> --> 
                    <!-- 						</select> -->

                    <!-- 						<input type="button" id="runNtvd"	name="runNtvd" value="NTVD" onclick="cncDisplay.runNtvd();" />   -->
                    <!-- 						</div> -->
                    <!-- 					</div> -->
                    <!-- 				<div id="video-div"> -->
                    <!-- 					<video id="tab-video-player" height="500" width="850" -->
                    <%-- 						poster="<%=request.getContextPath()%>/resources/images/pak_army_logo.png" --%>
                    <!-- 						controls autoplay> -->
                    <!-- 						<source src="" /> -->
                    <!-- 						<p class="warning">Your browser does not support HTML5 video.</p> -->
                    <!-- 					</video> -->
                    <!-- 				</div> -->

                    <!-- 			</div> -->
                    <!-- 		</div> -->

                    <div id="logs-tab" >
                        <div id="log-console-div" class="console">
                            <div id="log-console-message-div" style="overflow-y:scroll;"></div>
                        </div>
                    </div>

                </div>

            </td></tr>

        <tr style="background: silver; color: black;"><td colspan="3" width="100%">
                <div align="center">
                    Coppyright&copy; 2016, All rights reserved by Military College of Signals
                </div>
            </td>
        </tr>
        <tr style="background: silver; color: black;"><td colspan="3" width="100%">
                <div align="center">
                    <font size="1" >Developed by Capt Bilal</font> 
                </div>
            </td>
        </tr>
    </table>
    <script type="text/javascript">
        cncDisplay.setUserRole("${userRole}");
        cncDisplay.setUsername("${username}");
        cncDisplay.initializeMainDisplay();
    </script>

    <script>

    </script>

</body>
</html>
