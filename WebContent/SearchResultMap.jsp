<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Search Result in Map</title>
<script src="https://d3js.org/d3.v4.min.js"></script>
<style>
#lookLikeButton {
	font: bold 11px Arial;
	text-decoration: none;
	background-color: #EEEEEE;
	color: #333333;
	padding: 2px 6px 2px 6px;
	border-top: 1px solid #CCCCCC;
	border-right: 1px solid #333333;
	border-bottom: 1px solid #333333;
	border-left: 1px solid #CCCCCC;
}

.graticule {
  fill: none;
  stroke: #777;
  stroke-opacity: .5;
  stroke-width: .5px;
}
</style>
</head>
<body>
	<a id="lookLikeButton" href='SearchResult.jsp'>Go Back</a>
	<div id="map"></div>

	<script type="text/javascript">

            //Width and height
            var w = 1200;
            var h = 720;
            
            // variables for catching min and max zoom factors
           /*  var minZoom;
            var maxZoom; */

            var projection = d3
            .geoEquirectangular()
            .center([0, 15]) // set centre to further North as we are cropping more off bottom of map
            .scale([w / (2 * Math.PI)]) // scale to fit group width
            .translate([w / 2, h / 2]) // ensure centred in group
          ;

            //Define default path generator
            var path = d3.geoPath()
                        .projection(projection);


            //creating graticule element
            //var graticule=d3.geo.graticule();
            
            //Create SVG element
            var svg = d3.select("#map")
                        .append("svg")
                        .attr("width", w)
                        .attr("height", h);

            /* svg.append("path")
            .datum(graticule)
            .attr("#map", "graticule")
            .attr("d", path); */
            
            var rectangle = svg.append("rect")
                        .attr("height", h).attr("width", w)
                        .attr("fill", "transparent")
                        .attr("stroke", "black")
                        .attr("stroke-width", 1);

            //Load in GeoJSON data
            d3.json("custom.geo.json", function(json) {

                //Bind data and create one path per GeoJSON feature
                svg.selectAll("path")
                   .data(json.features)
                   .enter()
                   .append("path")
                   .attr("d", path)

        });

        </script>
</body>
</html>