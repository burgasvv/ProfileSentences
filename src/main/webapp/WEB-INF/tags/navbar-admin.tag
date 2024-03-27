<div>
    <nav style="height: 60px; background-color: lavender; box-shadow: 5px 5px 5px lightgray; border-radius: 20px">
        <div style="font-family: 'JetBrains Mono ExtraBold',sans-serif; font-size: large">
            <ul style="text-align: right; padding-top: 20px; padding-right: 100px">
                <li style="display: inline; padding-right: 1500px">
                    <a style="text-decoration: 0; color: dimgrey" href="${pageContext.request.contextPath}/index.jsp">Home</a>
                </li>
                <li style="display: inline; padding-right: 10px">
                    <a style="text-decoration: 0; color: dimgrey" href="${pageContext.request.contextPath}/log_out-servlet">Log Out</a>
                </li>
                <li style="display: inline">
                    <a style="text-decoration: 0; font-family: Corbel,serif; color: dimgrey" href="${pageContext.request.contextPath}/profile-page.jsp">${sessionScope.get("userName")}</a>
                </li>
                <li style="display: inline">
                    <h5 style="text-decoration: 0; font-family: Corbel,serif; color: dimgrey">${sessionScope.get("userType")}</h5>
                </li>
            </ul>
        </div>
    </nav>
</div>