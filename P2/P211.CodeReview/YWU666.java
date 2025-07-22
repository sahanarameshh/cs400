public class Frontend implements FrontendInterface {
    private BackendInterface backend;

    /**
     * Implementing classes should support the constructor below.
     * @param backend is used for shortest path computations
     */
    public Frontend(BackendInterface backend) {
        this.backend = backend;
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="start", for the start location
     * - a text input field with the id="end", for the destination
     * - a button labelled "Find Shortest Path" to request this computation
     * Ensure that these text fields are clearly labelled, so that the user
     * can understand how to use them.
     * @return an HTML string that contains input controls that the user can
     *         make use of to request a shortest path computation
     */
    @Override
    public String generateShortestPathPromptHTML() {
        return "<label for='start'>Start Location:</label><input type='text' id='start' name='start' />" +
               "<label for='end'>End Location:</label><input type='text' id='end' name='end' />" +
               "<button>Find Shortest Path</button>";
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) that describes the path's start and end locations
     * - an ordered list (ol) of locations along that shortest path
     * - a paragraph (p) that includes the total travel time along this path
     * Or if there is no such path, the HTML returned should instead indicate 
     * the kind of problem encountered.
     * @param start is the starting location to find a shortest path from
     * @param end is the destination that this shortest path should end at
     * @return an HTML string that describes the shortest path between these
     *         two locations
     */
    @Override
    public String generateShortestPathResponseHTML(String start, String end) {
        try {
            var locations = backend.findLocationsOnShortestPath(start, end);
            var times = backend.findTimesOnShortestPath(start, end);

            if (locations.isEmpty()) return "<p>No path found between " + start + " and " + end + ".</p>";

            StringBuilder html = new StringBuilder();
            html.append("<p>Shortest path from ").append(start).append(" to ").append(end).append(":</p>");
            html.append("<ol>");
            for (String loc : locations) html.append("<li>").append(loc).append("</li>");
            html.append("</ol>");

            double total = times.stream().mapToDouble(Double::doubleValue).sum();
            html.append("<p>Total travel time: ").append(total).append(" seconds.</p>");

            return html.toString();
        } catch (Exception e) {
            return "<p>Error computing shortest path: " + e.getMessage() + "</p>";
        }
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="from", for the start locations
     * - a text input field with the id="time", for the max time limit
     * - a button labelled "Reachable From Within" to submit this request
     * Ensure that these text fields are clearly labelled, so that the user
     * can understand how to use them.
     * @return an HTML string that contains input controls that the user can
     *         make use of to request a ten closest destinations calculation
     */
    @Override
    public String generateReachableFromWithinPromptHTML() {
        return "<label for='from'>Start Location:</label><input type='text' id='from' name='from' />" +
               "<label for='time'>Max Travel Time (seconds):</label><input type='text' id='time' name='time' />" +
               "<button>Reachable From Within</button>";
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) describing the start location and travel time allowed
     * - an unordered list (ul) of destinations that can be reached within
     *        that allowed travel time
     * Or if no such destinations can be found, the HTML returned should 
     * instead indicate the kind of problem encountered.
     * @param start is the starting location to search from
     * @param travelTime is the maximum number of seconds away from the start
     *        that will allow a destination to be reported
     * @return an HTML string that describes the closest destinations from the
     *         specified start location.
     */  
    @Override
    public String generateReachableFromWithinResponseHTML(String start, double travelTime) {
        try {
            var locations = backend.getReachableFromWithin(start, travelTime);
            if (locations.isEmpty()) return "<p>No locations reachable from " + start + " within " + travelTime + " seconds.</p>";

            StringBuilder html = new StringBuilder();
            html.append("<p>Locations reachable from ").append(start).append(" within ")
                .append(travelTime).append(" seconds:</p><ul>");
            for (String loc : locations) html.append("<li>").append(loc).append("</li>");
            html.append("</ul>");

            return html.toString();
        } catch (Exception e) {
            return "<p>Error: " + e.getMessage() + "</p>";
        }
    }
}