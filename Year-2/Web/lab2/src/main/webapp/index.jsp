<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="data.Result" %>
<%@ page import="data.ResultList" %>

<html lang="en">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="assets/favicon-32.ico">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
    <title>Web Lab #2</title>
</head>

<body onload="onLoad()">
<table id="main-grid">
    <tr>
        <!-- Header -->
        <td id="header-plate" colspan="2">
            <img class="left-aligned" src="assets/favicon-32.ico" width="32" height="32">
            <span class="left-aligned">Roman Vassilchenko | P32091</span>
            <span class="right-aligned">№1702</span>
        </td>
        <td>
        </td>
    </tr>

    <tr>
        <!-- Graph -->
        <td class="content-plate" id="graph-plate" rowspan="2">
            <div class="plate-top">
                <h2 class="plate-top-title">Graph</h2>
            </div>

            <div class="image-container">
<%--                <img src="assets/graph.svg" width="640" height="640">--%>
    <canvas id="plot" width="300" height="300"></canvas>
            </div>
        </td>
        <!-- Table -->
        <td class="content-plate table-view" id="table-plate">
            <div class="plate-top">
                <%=(request.getAttribute("errorMessage") != null)
                        ?
                        ("<h2 class=\"miss-text plate-top\">Table -> "+request.getAttribute("errorMessage")+"</h2>")
                        :
                        "<h2 class=\"plate-top-title\">Table</h2>"
                %>
                <h2 class="plate-top-title">Table</h2>
            </div>

            <div class="scroll-container">
                <table id="result-table">
                    <thead>
                    <tr class="table-header">
                        <th class="coords-col">X</th>
                        <th class="coords-col">Y</th>
                        <th class="coords-col">R</th>
                        <th class="time-col">Current time</th>
                        <th class="time-col">Execution time</th>
                        <th class="hitres-col">Hit result</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        ResultList resultList;
                        if (session.getAttribute("results") == null) {
                            resultList = new ResultList();
                        } else {
                            resultList = (ResultList) session.getAttribute("results");
                        }

                        for (Result result : resultList) {
                    %>
                    <tr>
                        <td class="table-text"><%=result.getX()%></td>
                        <td class="table-text"><%=result.getY()%></td>
                        <td class="table-text"><%=result.getR()%></td>
                        <td class="table-text"><%=result.getCurrTime()%></td>
                        <td class="table-text"><%=result.getExecTime()%></td>
                        <%=result.isHitResult() ? "<td class='hit-text'>true</span>" : "<td class='miss-text'>false</td>"%>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </td>
    </tr>

    <tr>
        <!-- Values -->
        <td class="content-plate values-view" id="values-plate">
            <div class="plate-top">
                <h2 class="plate-top-title">Values</h2>
            </div>

            <form id="input-form" method="get" action="${pageContext.request.contextPath}/process">
                <table id="input-grid">
                    <!-- X Values -->
                    <tr>
                        <td class="input-grid-label">
                            <label for="x-textinput">X:</label>
                        </td>

                        <td class="input-grid-value">
                            <input id="x-textinput" type="text" name="x" maxlength="10" autocomplete="off"
                                   placeholder="Number in range (-5 ... 5)">
                        </td>
                    </tr>

                    <!-- Y Values -->

                    <tr>
                        <td class="input-grid-label">
                            <label>Y:</label>
                        </td>
                        <td class="input-grid-value">
                            <select required name="y" id="y">
                                <option value="-2">-2</option>
                                <option value="-1.5">-1.5</option>
                                <option value="-1">-1</option>
                                <option value="-0.5">-0.5</option>
                                <option value="0">0</option>
                                <option value="0.5">-0.5</option>
                                <option value="1">1</option>
                                <option value="1.5">1.5</option>
                                <option value="2">2</option>
                            </select>
                        </td>
                    </tr>

                    <!-- R Values -->
                    <tr>
                        <td class="input-grid-label">
                            <label>R:</label>
                        </td>
                        <td class="input-grid-value r-radio-group">
                            <div class="center-labeled">
                                <label class="rbox-label" for="r-radio1">1</label>
                                <input class="r-radio" id="r-radio1" type="radio" name="r" value="1">
                            </div>
                            <div class="center-labeled">
                                <label class="rbox-label" for="r-radio2">2</label>
                                <input class="r-radio" id="r-radio2" type="radio" name="r" value="2">
                            </div>
                            <div class="center-labeled">
                                <label class="rbox-label" for="r-radio3">3</label>
                                <input class="r-radio" id="r-radio3" type="radio" name="r" value="3">
                            </div>
                            <div class="center-labeled">
                                <label class="rbox-label" for="r-radio4">4</label>
                                <input class="r-radio" id="r-radio4" type="radio" name="r" value="4">
                            </div>
                            <div class="center-labeled">
                                <label class="rbox-label" for="r-radio5">5</label>
                                <input class="r-radio" id="r-radio5" type="radio" name="r" value="5">
                            </div>
                        </td>
                    </tr>

                    <!-- Buttons -->
                    <tr>
                        <td colspan="2">
                            <div class="buttons">
                                <input id="submitBtn" class="button" type="submit" value="Submit">
                                <input id="resetBtn" class="button" type="reset" value="Reset">
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type='text/javascript' src="js/main.js"></script>
<script type='text/javascript' src="js/graph.js"></script>
<script type="text/javascript">
    $(document).ready(drawGraph());
</script>
</body>

</html>