/**
 * The Santa Lucia maps implementation using
 * the Google Maps API v3. This is injected into
 * Java using the JavaFX WebEngine
 *
 * @author Brett Downey
 * @author Zachery Unrau
 */

let map;
let infoWindow;
let geocoder;
let stores;
let markers = [];
let winnipeg = {
  lat: 49.883338,
  lng: -97.135235
};
let saskatoon = {
  lat: 52.132617,
  lng: -106.664245
};

function initialize() {
  map = new google.maps.Map(document.getElementById('driver'), {
    zoom: 11,
    center: winnipeg,
    disableDefaultUI: true
  });
  geocoder = new google.maps.Geocoder();
  infoWindow = new google.maps.InfoWindow();

  buildAutoComplete();

  stores = [{
    type: 'henderson',
    poly: undefined,
    name: '805 Henderson Hwy',
    areaName: '805 Henderson Hwy Area',
    lat: 49.929638,
    lng: -97.101861,
    color: '#FF0000'
  },
    {
      type: 'mainstreet',
      poly: undefined,
      name: '1473 Main Street',
      areaName: '1473 Main Street Area',
      lat: 49.927860,
      lng: -97.123497,
      color: '#91ff00'
    },
    {
      type: 'stmarys',
      poly: undefined,
      name: '4 St. Mary’s Rd',
      areaName: '4 St. Mary’s Rd Area',
      lat: 49.880907,
      lng: -97.128428,
      color: '#ffa200'
    },
    {
      type: 'regent',
      poly: undefined,
      name: '108 Regent Ave East',
      areaName: '108 Regent Ave East Area',
      lat: 49.894950,
      lng: -97.001837,
      color: '#00ff95'
    },
    {
      type: 'pembina',
      poly: undefined,
      name: '40 – 2589 Pembina Hwy',
      areaName: '40 – 2589 Pembina Hwy Area',
      lat: 49.798449,
      lng: -97.158111,
      color: '#00ff95'
    },
    {
      type: 'portage',
      poly: undefined,
      name: '2029 Portage Avenue',
      areaName: '2029 Portage Avenue Area',
      lat: 49.877834,
      lng: -97.229729,
      color: '#009dff'
    },
    {
      type: 'corydon',
      poly: undefined,
      name: '905 Corydon Avenue',
      areaName: '905 Corydon Avenue Area',
      lat: 49.868981,
      lng: -97.159630,
      color: '#8400ff'
    },
    {
      type: 'saskatoon_east',
      poly: undefined,
      name: '3414, 2 8th Street Saskatoon East',
      areaName: '3414, 2 8th Street Saskatoon East Area',
      lat: 52.114267,
      lng: -106.597749,
      color: '#FF0000'
    },
    {
      type: 'saskatoon_west',
      poly: undefined,
      name: '2124B 22 Street Saskatoon West',
      areaName: '2124B 22 Street Saskatoon West Area',
      lat: 52.129644,
      lng: -106.703703,
      color: '#91ff00'
    }
  ];

  buildMaps();
}

function buildAutoComplete() {
  var card = document.getElementById('pac-card');
  var input = document.getElementById('pac-input');
  var types = document.getElementById('type-selector');
  var strictBounds = document.getElementById('strict-bounds-selector');
  map.controls[google.maps.ControlPosition.TOP_CENTER].push(card);
  var autocomplete = new google.maps.places.Autocomplete(input);
  autocomplete.bindTo('bounds', map);
  autocomplete.setFields(
      ['address_components', 'geometry', 'icon', 'name']);
  var infowindowContent = document.getElementById('infowindow-content');
  infoWindow.setContent(infowindowContent);
  var marker = new google.maps.Marker({
    map: map,
    anchorPoint: new google.maps.Point(0, -29)
  });

  autocomplete.addListener('place_changed', function () {
    infoWindow.close();
    marker.setVisible(false);
    var place = autocomplete.getPlace();
    if (!place.geometry) {
      return;
    }

    if (place.geometry.viewport) {
      map.fitBounds(place.geometry.viewport);
    } else {
      map.setCenter(place.geometry.location);
      map.setZoom(17);
    }
    marker.setPosition(place.geometry.location);
    marker.setVisible(true);

    var address = '';
    if (place.address_components) {
      address = [
        (place.address_components[0] && place.address_components[0].short_name
            || ''),
        (place.address_components[1] && place.address_components[1].short_name
            || ''),
        (place.address_components[2] && place.address_components[2].short_name
            || '')
      ].join(' ');
    }

    infowindowContent.children['place-icon'].src = place.icon;
    infowindowContent.children['place-name'].textContent = place.name;
    infowindowContent.children['place-address'].textContent = address;
    autocompleteStore(infowindowContent, place.geometry.location);
    infoWindow.open(map, marker);
  });

  document.getElementById('use-strict-bounds')
      .addEventListener('click', function () {
        console.log('Checkbox clicked! New state=' + this.checked);
        autocomplete.setOptions({strictBounds: this.checked});
      });
}

/**
 * Centers the driverMap at winnipeg
 */
function goWinnipeg() {
  map.setCenter(winnipeg);
  map.setZoom(11);
}

/**
 * Opens the Santa Lucia pizza website within a popup
 */
function openWebsite() {
  window.open('https://www.santaluciapizza.com/', '1563527672085',
      'width=700,height=500,toolbar=0,menubar=0,location=0,status=1,scrollbars=1,resizable=1,left=0,top=0');
}

/**
 * Finds a location on the driverMap given an address.
 * This responds to Java with the status of the given address,
 * which area it is contained in, and most importantly, if the address
 * even exists. If the address is found, a pin of the address will be placed on
 * the driverMap.
 * TODO Deprecated
 * @param input The input to give to the geocoder
 */
function findLocation(input) {
  geocoder.geocode({
    'address': input
  }, function (results, status) {
    let response = {};

    let type = results[0].geometry.location_type;
    if (type === 'GEOMETRIC_CENTER') {
      /* The address does not exist */
      removeAllMarkers();
      javaBridge.geocodeCallBack(response);
      return;
    } else if (type !== 'ROOFTOP' && type !== 'RANGE_INTERPOLATED') {
      /* Google Maps could not find an accurate response for this, but it may exist, continue */
      response.warningMessage = 'A precise location could not be determined.';
    }

    if (status === 'OK') {
      removeAllMarkers();
      let location = results[0].geometry.location;
      let latLng = new google.maps.LatLng(location.lat(), location.lng());
      let marker = new google.maps.Marker({position: latLng, map: map});
      markers.push(marker);
      map.setCenter(latLng);
      map.setZoom(14);

      for (const store of stores) {
        if (google.maps.geometry.poly.containsLocation(latLng, store.poly)) {
          infowindowContent.children['place-address'].textContent = address;
          response.json = JSON.stringify(results);
          response.type = store.type;
          response.name = store.name;
          finishMarker(marker, store.type);
          break;
        }
      }
    }

    javaBridge.geocodeCallBack(response);
  });
}

function autocompleteStore(markerContent, location) {
  for (const store of stores) {
    if (google.maps.geometry.poly.containsLocation(location, store.poly)) {
      markerContent.children['store-name'].textContent = store.name + '\'s delivery location';
      break;
    }
  }
}

function finishMarker(marker, type) {
  google.maps.event.addListener(marker, 'click', function (event) {
    output(event, type);
    clickFocus(event, 14);
  });
}

function removeAllMarkers() {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
}

function buildMaps() {
  let centerControlDiv = document.createElement('div');
  new SwitchMapController(centerControlDiv);
  centerControlDiv.index = 1;
  centerControlDiv.style['padding-top'] = '10px';
  map.controls[google.maps.ControlPosition.BOTTOM_CENTER].push(
      centerControlDiv);

  hendersonMap();
  stmarysMap();
  mainMap();
  regentMap();
  pembinaMap();
  portageMap();
  corydonMap();
  saskatoonEast();
  saskatoonWest();

  setIcons();
}

function setIcons() {
  let image = {
    url: 'https://media.discordapp.net/attachments/575142030905442325/597226192030138368/slpedit.png',
    size: new google.maps.Size(20, 23),
    origin: new google.maps.Point(0, 0),
    anchor: new google.maps.Point(10, 0)
  };
  for (const store of stores) {
    let marker = new google.maps.Marker({
      position: {
        lat: store.lat,
        lng: store.lng
      },
      map: map,
      icon: image,
      title: store.name
    });

    google.maps.event.addListener(marker, 'click', function (event) {
      output(event, store.name);
      clickFocus(event, 17);
    });
  }
}

function finishMap(coords, type) {
  for (const store of stores) {
    if (store.type === type) {
      let polygon = new google.maps.Polygon({
        paths: coords,
        strokeColor: store.color,
        strokeOpacity: 0.75,
        strokeWeight: 2,
        fillColor: store.color,
        fillOpacity: 0.05
      });

      polygon.setMap(map);
      store.poly = polygon;

      google.maps.event.addListener(polygon, 'click', function (event) {
        output(event, type);
        clickFocus(event, 14);
      });
    }
  }
}

function hendersonMap() {
  var hendersonCoords = [{
    lat: 49.99537,
    lng: -97.0401
  },
    {
      lat: 49.99107,
      lng: -97.04946
    },
    {
      lat: 49.98307,
      lng: -97.05358
    },
    {
      lat: 49.97775,
      lng: -97.06054
    },
    {
      lat: 49.96871,
      lng: -97.06546
    },
    {
      lat: 49.96012,
      lng: -97.07315
    },
    {
      lat: 49.95124,
      lng: -97.08451
    },
    {
      lat: 49.95126,
      lng: -97.09578
    },
    {
      lat: 49.94828,
      lng: -97.0987
    },
    {
      lat: 49.94361,
      lng: -97.09617
    },
    {
      lat: 49.94044,
      lng: -97.09862
    },
    {
      lat: 49.93744,
      lng: -97.10968
    },
    {
      lat: 49.93106,
      lng: -97.11144
    },
    {
      lat: 49.92531,
      lng: -97.11011
    },
    {
      lat: 49.92072,
      lng: -97.11457
    },
    {
      lat: 49.91851,
      lng: -97.12431
    },
    {
      lat: 49.91474,
      lng: -97.12753
    },
    {
      lat: 49.9117,
      lng: -97.12616
    },
    {
      lat: 49.9086,
      lng: -97.12091
    },
    {
      lat: 49.9065,
      lng: -97.12327
    },
    {
      lat: 49.90432,
      lng: -97.12573
    },
    {
      lat: 49.9035,
      lng: -97.12613
    },
    {
      lat: 49.90066,
      lng: -97.12729
    },
    {
      lat: 49.90037,
      lng: -97.11293
    },
    {
      lat: 49.90127,
      lng: -97.10929
    },
    {
      lat: 49.9027,
      lng: -97.10856
    },
    {
      lat: 49.90243,
      lng: -97.1047
    },
    {
      lat: 49.9,
      lng: -97.1016
    },
    {
      lat: 49.89947,
      lng: -97.10177
    },
    {
      lat: 49.89411,
      lng: -97.07084
    },
    {
      lat: 49.90645,
      lng: -97.06255
    },
    {
      lat: 49.90786,
      lng: -97.05946
    },
    {
      lat: 49.90996,
      lng: -97.05688
    },
    {
      lat: 49.91438,
      lng: -97.05431
    },
    {
      lat: 49.91568,
      lng: -97.05302
    },
    {
      lat: 49.91844,
      lng: -97.04946
    },
    {
      lat: 49.92058,
      lng: -97.04841
    },
    {
      lat: 49.92671,
      lng: -97.04806
    },
    {
      lat: 49.92835,
      lng: -97.04716
    },
    {
      lat: 49.9416,
      lng: -97.03579
    },
    {
      lat: 49.96489,
      lng: -97.0166
    },
    {
      lat: 49.96637,
      lng: -97.0135
    },
    {
      lat: 49.96701,
      lng: -97.01004
    },
    {
      lat: 49.96687,
      lng: -96.9903
    },
    {
      lat: 49.97456,
      lng: -96.99014
    },
    {
      lat: 49.97994,
      lng: -97.00153
    }
  ];

  finishMap(hendersonCoords, 'henderson');
}

function stmarysMap() {
  var stMarysCoords = [{
    lat: 49.82237,
    lng: -97.02099
  },
    {
      lat: 49.82261,
      lng: -97.02709
    },
    {
      lat: 49.80136,
      lng: -97.08843
    },
    {
      lat: 49.80522,
      lng: -97.09076
    },
    {
      lat: 49.80736,
      lng: -97.09164
    },
    {
      lat: 49.80926,
      lng: -97.0927
    },
    {
      lat: 49.81185,
      lng: -97.09469
    },
    {
      lat: 49.81696,
      lng: -97.09883
    },
    {
      lat: 49.81789,
      lng: -97.09931
    },
    {
      lat: 49.81916,
      lng: -97.09918
    },
    {
      lat: 49.8205,
      lng: -97.0991
    },
    {
      lat: 49.82142,
      lng: -97.09949
    },
    {
      lat: 49.82279,
      lng: -97.10064
    },
    {
      lat: 49.82033,
      lng: -97.10772
    },
    {
      lat: 49.82026,
      lng: -97.1084
    },
    {
      lat: 49.82022,
      lng: -97.10931
    },
    {
      lat: 49.82081,
      lng: -97.11125
    },
    {
      lat: 49.82033,
      lng: -97.11324
    },
    {
      lat: 49.81938,
      lng: -97.11316
    },
    {
      lat: 49.81658,
      lng: -97.12346
    },
    {
      lat: 49.81356,
      lng: -97.1299
    },
    {
      lat: 49.8127,
      lng: -97.13578
    },
    {
      lat: 49.81376,
      lng: -97.13999
    },
    {
      lat: 49.81528,
      lng: -97.14149
    },
    {
      lat: 49.81943,
      lng: -97.14183
    },
    {
      lat: 49.82829,
      lng: -97.1481
    },
    {
      lat: 49.83128,
      lng: -97.14741
    },
    {
      lat: 49.83222,
      lng: -97.14293
    },
    {
      lat: 49.83124,
      lng: -97.13415
    },
    {
      lat: 49.83289,
      lng: -97.13134
    },
    {
      lat: 49.83503,
      lng: -97.13115
    },
    {
      lat: 49.83849,
      lng: -97.13474
    },
    {
      lat: 49.84111,
      lng: -97.1374
    },
    {
      lat: 49.84328,
      lng: -97.13551
    },
    {
      lat: 49.84339,
      lng: -97.13004
    },
    {
      lat: 49.84173,
      lng: -97.12228
    },
    {
      lat: 49.8434,
      lng: -97.11947
    },
    {
      lat: 49.84694,
      lng: -97.12081
    },
    {
      lat: 49.85141,
      lng: -97.12864
    },
    {
      lat: 49.85253,
      lng: -97.13425
    },
    {
      lat: 49.85017,
      lng: -97.14085
    },
    {
      lat: 49.85038,
      lng: -97.14491
    },
    {
      lat: 49.85287,
      lng: -97.1446
    },
    {
      lat: 49.85361,
      lng: -97.14665
    },
    {
      lat: 49.85202,
      lng: -97.15133
    },
    {
      lat: 49.8524,
      lng: -97.15178
    },
    {
      lat: 49.85303,
      lng: -97.1519
    },
    {
      lat: 49.86416,
      lng: -97.14543
    },
    {
      lat: 49.8646,
      lng: -97.14766
    },
    {
      lat: 49.86537,
      lng: -97.14736
    },
    {
      lat: 49.86698,
      lng: -97.14686
    },
    {
      lat: 49.87269,
      lng: -97.15141
    },
    {
      lat: 49.87265,
      lng: -97.15254
    },
    {
      lat: 49.8741,
      lng: -97.1537
    },
    {
      lat: 49.87379,
      lng: -97.15533
    },
    {
      lat: 49.87417,
      lng: -97.15746
    },
    {
      lat: 49.87601,
      lng: -97.15951
    },
    {
      lat: 49.87776,
      lng: -97.16109
    },
    {
      lat: 49.87845,
      lng: -97.16113
    },
    {
      lat: 49.88583,
      lng: -97.1602
    },
    {
      lat: 49.88597,
      lng: -97.16274
    },
    {
      lat: 49.88642,
      lng: -97.16406
    },
    {
      lat: 49.88452,
      lng: -97.17216
    },
    {
      lat: 49.88288,
      lng: -97.18405
    },
    {
      lat: 49.9081,
      lng: -97.18223
    },
    {
      lat: 49.90935,
      lng: -97.18364
    },
    {
      lat: 49.91351,
      lng: -97.18376
    },
    {
      lat: 49.91702,
      lng: -97.18076
    },
    {
      lat: 49.91872,
      lng: -97.17851
    },
    {
      lat: 49.91542,
      lng: -97.16869
    },
    {
      lat: 49.91258,
      lng: -97.16165
    },
    {
      lat: 49.91034,
      lng: -97.16008
    },
    {
      lat: 49.90178,
      lng: -97.13353
    },
    {
      lat: 49.90299,
      lng: -97.12816
    },
    {
      lat: 49.90363,
      lng: -97.12679
    },
    {
      lat: 49.90432,
      lng: -97.12573
    },
    {
      lat: 49.9035,
      lng: -97.12613
    },
    {
      lat: 49.90066,
      lng: -97.12729
    },
    {
      lat: 49.90037,
      lng: -97.11293
    },
    {
      lat: 49.90127,
      lng: -97.10929
    },
    {
      lat: 49.9027,
      lng: -97.10856
    },
    {
      lat: 49.90243,
      lng: -97.1047
    },
    {
      lat: 49.9,
      lng: -97.1016
    },
    {
      lat: 49.89947,
      lng: -97.10177
    },
    {
      lat: 49.89411,
      lng: -97.07084
    },
    {
      lat: 49.8883,
      lng: -97.0722
    },
    {
      lat: 49.87748,
      lng: -97.07199
    },
    {
      lat: 49.87389,
      lng: -97.02711
    },
    {
      lat: 49.86877,
      lng: -97.0274
    },
    {
      lat: 49.85969,
      lng: -97.02106
    }
  ];

  finishMap(stMarysCoords, 'stmarys');
}

function regentMap() {
  var regentCoords = [{
    lat: 49.96685,
    lng: -96.98424
  },
    {
      lat: 49.9588,
      lng: -96.98443
    },
    {
      lat: 49.92141,
      lng: -96.96494
    },
    {
      lat: 49.91577,
      lng: -96.96713
    },
    {
      lat: 49.90084,
      lng: -96.95934
    },
    {
      lat: 49.85391,
      lng: -96.95894
    },
    {
      lat: 49.85969,
      lng: -97.02106
    },
    {
      lat: 49.86877,
      lng: -97.0274
    },
    {
      lat: 49.87389,
      lng: -97.02711
    },
    {
      lat: 49.87748,
      lng: -97.07199
    },
    {
      lat: 49.8883,
      lng: -97.0722
    },
    {
      lat: 49.89411,
      lng: -97.07084
    },
    {
      lat: 49.90645,
      lng: -97.06255
    },
    {
      lat: 49.90786,
      lng: -97.05946
    },
    {
      lat: 49.90996,
      lng: -97.05688
    },
    {
      lat: 49.91438,
      lng: -97.05431
    },
    {
      lat: 49.91568,
      lng: -97.05302
    },
    {
      lat: 49.91844,
      lng: -97.04946
    },
    {
      lat: 49.92058,
      lng: -97.04841
    },
    {
      lat: 49.92671,
      lng: -97.04806
    },
    {
      lat: 49.92835,
      lng: -97.04716
    },
    {
      lat: 49.9416,
      lng: -97.03579
    },
    {
      lat: 49.96489,
      lng: -97.0166
    },
    {
      lat: 49.96637,
      lng: -97.0135
    },
    {
      lat: 49.96701,
      lng: -97.01004
    },
    {
      lat: 49.96687,
      lng: -96.9903
    }
  ];

  finishMap(regentCoords, 'regent');
}

function mainMap() {
  var mainCoords = [{
    lat: 49.97775,
    lng: -97.06054
  },
    {
      lat: 49.96871,
      lng: -97.06546
    },
    {
      lat: 49.96012,
      lng: -97.07315
    },
    {
      lat: 49.95124,
      lng: -97.08451
    },
    {
      lat: 49.95126,
      lng: -97.09578
    },
    {
      lat: 49.94828,
      lng: -97.0987
    },
    {
      lat: 49.94361,
      lng: -97.09617
    },
    {
      lat: 49.94044,
      lng: -97.09862
    },
    {
      lat: 49.93744,
      lng: -97.10968
    },
    {
      lat: 49.93106,
      lng: -97.11144
    },
    {
      lat: 49.92531,
      lng: -97.11011
    },
    {
      lat: 49.92072,
      lng: -97.11457
    },
    {
      lat: 49.91851,
      lng: -97.12431
    },
    {
      lat: 49.91474,
      lng: -97.12753
    },
    {
      lat: 49.9117,
      lng: -97.12616
    },
    {
      lat: 49.9086,
      lng: -97.12091
    },
    {
      lat: 49.9065,
      lng: -97.12327
    },
    {
      lat: 49.90432,
      lng: -97.12573
    },
    {
      lat: 49.90363,
      lng: -97.12679
    },
    {
      lat: 49.90299,
      lng: -97.12816
    },
    {
      lat: 49.90178,
      lng: -97.13353
    },
    {
      lat: 49.91034,
      lng: -97.16008
    },
    {
      lat: 49.91258,
      lng: -97.16165
    },
    {
      lat: 49.91542,
      lng: -97.16869
    },
    {
      lat: 49.91872,
      lng: -97.17851
    },
    {
      lat: 49.92695,
      lng: -97.20681
    },
    {
      lat: 49.92854,
      lng: -97.20724
    },
    {
      lat: 49.94038,
      lng: -97.22977
    },
    {
      lat: 49.94594,
      lng: -97.22958
    },
    {
      lat: 49.96078,
      lng: -97.22956
    },
    {
      lat: 49.99178,
      lng: -97.22924
    },
    {
      lat: 49.99248,
      lng: -97.16099
    },
    {
      lat: 50.01707,
      lng: -97.16093
    },
    {
      lat: 50.01152,
      lng: -97.13847
    }
  ];

  finishMap(mainCoords, 'mainstreet');
}

function pembinaMap() {
  var pembinaCoords = [{
    lat: 49.80136,
    lng: -97.08843
  },
    {
      lat: 49.80522,
      lng: -97.09076
    },
    {
      lat: 49.80736,
      lng: -97.09164
    },
    {
      lat: 49.80926,
      lng: -97.0927
    },
    {
      lat: 49.81185,
      lng: -97.09469
    },
    {
      lat: 49.81696,
      lng: -97.09883
    },
    {
      lat: 49.81789,
      lng: -97.09931
    },
    {
      lat: 49.81916,
      lng: -97.09918
    },
    {
      lat: 49.8205,
      lng: -97.0991
    },
    {
      lat: 49.82142,
      lng: -97.09949
    },
    {
      lat: 49.82279,
      lng: -97.10064
    },
    {
      lat: 49.82033,
      lng: -97.10772
    },
    {
      lat: 49.82026,
      lng: -97.1084
    },
    {
      lat: 49.82022,
      lng: -97.10931
    },
    {
      lat: 49.82081,
      lng: -97.11125
    },
    {
      lat: 49.82033,
      lng: -97.11324
    },
    {
      lat: 49.81938,
      lng: -97.11316
    },
    {
      lat: 49.81658,
      lng: -97.12346
    },
    {
      lat: 49.81356,
      lng: -97.1299
    },
    {
      lat: 49.8127,
      lng: -97.13578
    },
    {
      lat: 49.81376,
      lng: -97.13999
    },
    {
      lat: 49.81528,
      lng: -97.14149
    },
    {
      lat: 49.81943,
      lng: -97.14183
    },
    {
      lat: 49.82829,
      lng: -97.1481
    },
    {
      lat: 49.83128,
      lng: -97.14741
    },
    {
      lat: 49.83222,
      lng: -97.14293
    },
    {
      lat: 49.83124,
      lng: -97.13415
    },
    {
      lat: 49.83289,
      lng: -97.13134
    },
    {
      lat: 49.83503,
      lng: -97.13115
    },
    {
      lat: 49.83849,
      lng: -97.13474
    },
    {
      lat: 49.84111,
      lng: -97.1374
    },
    {
      lat: 49.84328,
      lng: -97.13551
    },
    {
      lat: 49.84339,
      lng: -97.13004
    },
    {
      lat: 49.84173,
      lng: -97.12228
    },
    {
      lat: 49.8434,
      lng: -97.11947
    },
    {
      lat: 49.84694,
      lng: -97.12081
    },
    {
      lat: 49.85141,
      lng: -97.12864
    },
    {
      lat: 49.85253,
      lng: -97.13425
    },
    {
      lat: 49.85017,
      lng: -97.14085
    },
    {
      lat: 49.85038,
      lng: -97.14491
    },
    {
      lat: 49.85287,
      lng: -97.1446
    },
    {
      lat: 49.85361,
      lng: -97.14665
    },
    {
      lat: 49.85202,
      lng: -97.15133
    },
    {
      lat: 49.85025,
      lng: -97.15268
    },
    {
      lat: 49.84149,
      lng: -97.15355
    },
    {
      lat: 49.83564,
      lng: -97.17129
    },
    {
      lat: 49.83431,
      lng: -97.17503
    },
    {
      lat: 49.83274,
      lng: -97.17746
    },
    {
      lat: 49.82744,
      lng: -97.18323
    },
    {
      lat: 49.82137,
      lng: -97.2013
    },
    {
      lat: 49.8258,
      lng: -97.20502
    },
    {
      lat: 49.82883,
      lng: -97.20897
    },
    {
      lat: 49.83053,
      lng: -97.20977
    },
    {
      lat: 49.83792,
      lng: -97.20909
    },
    {
      lat: 49.83848,
      lng: -97.2234
    },
    {
      lat: 49.84505,
      lng: -97.23169
    },
    {
      lat: 49.80915,
      lng: -97.2342
    },
    {
      lat: 49.8134,
      lng: -97.22344
    },
    {
      lat: 49.76811,
      lng: -97.22495
    },
    {
      lat: 49.76822,
      lng: -97.18642
    },
    {
      lat: 49.76838,
      lng: -97.18425
    },
    {
      lat: 49.76856,
      lng: -97.18271
    },
    {
      lat: 49.75473,
      lng: -97.17952
    },
    {
      lat: 49.74808,
      lng: -97.17892
    },
    {
      lat: 49.7606,
      lng: -97.15028
    },
    {
      lat: 49.75965,
      lng: -97.14072
    },
    {
      lat: 49.77288,
      lng: -97.13953
    },
    {
      lat: 49.77412,
      lng: -97.14292
    },
    {
      lat: 49.77038,
      lng: -97.15077
    },
    {
      lat: 49.77099,
      lng: -97.15412
    },
    {
      lat: 49.77352,
      lng: -97.15425
    },
    {
      lat: 49.77656,
      lng: -97.14837
    },
    {
      lat: 49.77834,
      lng: -97.13446
    },
    {
      lat: 49.7801,
      lng: -97.13126
    },
    {
      lat: 49.78461,
      lng: -97.1351
    }
  ];

  finishMap(pembinaCoords, 'pembina');
}

function portageMap() {
  var portageCoords = [{
    lat: 49.87949,
    lng: -97.1838
  },
    {
      lat: 49.88295039436205,
      lng: -97.18353746678554
    },
    {
      lat: 49.88288,
      lng: -97.18405
    },
    {
      lat: 49.9081,
      lng: -97.18223
    },
    {
      lat: 49.90935,
      lng: -97.18364
    },
    {
      lat: 49.91351,
      lng: -97.18376
    },
    {
      lat: 49.91702,
      lng: -97.18076
    },
    {
      lat: 49.91872,
      lng: -97.17851
    },
    {
      lat: 49.92695,
      lng: -97.20681
    },
    {
      lat: 49.92908,
      lng: -97.32062
    },
    {
      lat: 49.91035,
      lng: -97.3214
    },
    {
      lat: 49.91039,
      lng: -97.34028
    },
    {
      lat: 49.85751,
      lng: -97.34288
    },
    {
      lat: 49.85689,
      lng: -97.32305
    },
    {
      lat: 49.85534,
      lng: -97.32288
    },
    {
      lat: 49.83882,
      lng: -97.32447
    },
    {
      lat: 49.84041,
      lng: -97.27951
    },
    {
      lat: 49.84349,
      lng: -97.24746
    },
    {
      lat: 49.87412,
      lng: -97.24685
    },
    {
      lat: 49.87458,
      lng: -97.24454
    },
    {
      lat: 49.87509,
      lng: -97.24347
    },
    {
      lat: 49.87527,
      lng: -97.23889
    },
    {
      lat: 49.87528,
      lng: -97.22966
    },
    {
      lat: 49.87332,
      lng: -97.22294
    },
    {
      lat: 49.87436,
      lng: -97.2129
    },
    {
      lat: 49.87369,
      lng: -97.21011
    },
    {
      lat: 49.87431,
      lng: -97.20827
    },
    {
      lat: 49.87635,
      lng: -97.20837
    },
    {
      lat: 49.87712,
      lng: -97.20625
    },
    {
      lat: 49.87827,
      lng: -97.19149
    }
  ];

  finishMap(portageCoords, 'portage');
}

function corydonMap() {
  var corydonCoords = [{
    lat: 49.85303,
    lng: -97.1519
  },
    {
      lat: 49.86416,
      lng: -97.14543
    },
    {
      lat: 49.8646,
      lng: -97.14766
    },
    {
      lat: 49.86537,
      lng: -97.14736
    },
    {
      lat: 49.86698,
      lng: -97.14686
    },
    {
      lat: 49.87269,
      lng: -97.15141
    },
    {
      lat: 49.87265,
      lng: -97.15254
    },
    {
      lat: 49.8741,
      lng: -97.1537
    },
    {
      lat: 49.87379,
      lng: -97.15533
    },
    {
      lat: 49.87417,
      lng: -97.15746
    },
    {
      lat: 49.87601,
      lng: -97.15951
    },
    {
      lat: 49.87776,
      lng: -97.16109
    },
    {
      lat: 49.87845,
      lng: -97.16113
    },
    {
      lat: 49.88583,
      lng: -97.1602
    },
    {
      lat: 49.88597,
      lng: -97.16274
    },
    {
      lat: 49.88642,
      lng: -97.16406
    },
    {
      lat: 49.88452,
      lng: -97.17216
    },
    {
      lat: 49.88295057378793,
      lng: -97.18353749287854
    },
    {
      lat: 49.87949,
      lng: -97.1838
    },
    {
      lat: 49.87827,
      lng: -97.19149
    },
    {
      lat: 49.87712,
      lng: -97.20625
    },
    {
      lat: 49.87635,
      lng: -97.20837
    },
    {
      lat: 49.87431,
      lng: -97.20827
    },
    {
      lat: 49.87369,
      lng: -97.21011
    },
    {
      lat: 49.87436,
      lng: -97.2129
    },
    {
      lat: 49.87332,
      lng: -97.22294
    },
    {
      lat: 49.87528,
      lng: -97.22966
    },
    {
      lat: 49.87527,
      lng: -97.23889
    },
    {
      lat: 49.87509,
      lng: -97.24347
    },
    {
      lat: 49.87458,
      lng: -97.24454
    },
    {
      lat: 49.87412,
      lng: -97.24685
    },
    {
      lat: 49.84349,
      lng: -97.24746
    },
    {
      lat: 49.84505,
      lng: -97.23169
    },
    {
      lat: 49.83848,
      lng: -97.2234
    },
    {
      lat: 49.83792,
      lng: -97.20909
    },
    {
      lat: 49.83053,
      lng: -97.20977
    },
    {
      lat: 49.82883,
      lng: -97.20897
    },
    {
      lat: 49.8258,
      lng: -97.20502
    },
    {
      lat: 49.82137,
      lng: -97.2013
    },
    {
      lat: 49.82744,
      lng: -97.18323
    },
    {
      lat: 49.83274,
      lng: -97.17746
    },
    {
      lat: 49.83431,
      lng: -97.17503
    },
    {
      lat: 49.83564,
      lng: -97.17129
    },
    {
      lat: 49.84149,
      lng: -97.15355
    },
    {
      lat: 49.85025,
      lng: -97.15268
    }
  ];

  finishMap(corydonCoords, 'corydon');
}

function saskatoonEast() {
  var saskatoonEastCoords = [{
    lat: 52.17579,
    lng: -106.60493
  },
    {
      lat: 52.16312,
      lng: -106.60922
    },
    {
      lat: 52.15901,
      lng: -106.6166
    },
    {
      lat: 52.1602,
      lng: -106.62814
    },
    {
      lat: 52.15372,
      lng: -106.63416
    },
    {
      lat: 52.12987,
      lng: -106.64936
    },
    {
      lat: 52.12239,
      lng: -106.65994
    },
    {
      lat: 52.12107,
      lng: -106.67144
    },
    {
      lat: 52.11657,
      lng: -106.67722
    },
    {
      lat: 52.10274,
      lng: -106.68622
    },
    {
      lat: 52.09869,
      lng: -106.69384
    },
    {
      lat: 52.0935,
      lng: -106.70049
    },
    {
      lat: 52.07203,
      lng: -106.64642
    },
    {
      lat: 52.07142,
      lng: -106.60324
    },
    {
      lat: 52.07497,
      lng: -106.55192
    },
    {
      lat: 52.08536,
      lng: -106.51936
    },
    {
      lat: 52.09984,
      lng: -106.52773
    },
    {
      lat: 52.13567,
      lng: -106.52747
    },
    {
      lat: 52.1604,
      lng: -106.50486
    },
    {
      lat: 52.1686,
      lng: -106.5113
    },
    {
      lat: 52.18506,
      lng: -106.5273
    },
    {
      lat: 52.18426,
      lng: -106.55242
    },
    {
      lat: 52.17676,
      lng: -106.56405
    },
    {
      lat: 52.17583,
      lng: -106.57508
    }
  ];

  finishMap(saskatoonEastCoords, 'saskatoon_east');
}

function saskatoonWest() {
  var saskatoonWestCoords = [{
    lat: 52.17579,
    lng: -106.60493
  },
    {
      lat: 52.16312,
      lng: -106.60922
    },
    {
      lat: 52.15901,
      lng: -106.6166
    },
    {
      lat: 52.1602,
      lng: -106.62814
    },
    {
      lat: 52.15372,
      lng: -106.63416
    },
    {
      lat: 52.12987,
      lng: -106.64936
    },
    {
      lat: 52.12239,
      lng: -106.65994
    },
    {
      lat: 52.12107,
      lng: -106.67144
    },
    {
      lat: 52.11657,
      lng: -106.67722
    },
    {
      lat: 52.10274,
      lng: -106.68622
    },
    {
      lat: 52.09869,
      lng: -106.69384
    },
    {
      lat: 52.0935,
      lng: -106.70049
    },
    {
      lat: 52.10702,
      lng: -106.74201
    },
    {
      lat: 52.1163,
      lng: -106.75995
    },
    {
      lat: 52.12294,
      lng: -106.76819
    },
    {
      lat: 52.15349,
      lng: -106.76716
    },
    {
      lat: 52.16093,
      lng: -106.72608
    },
    {
      lat: 52.17184,
      lng: -106.72485
    },
    {
      lat: 52.18037,
      lng: -106.73472
    },
    {
      lat: 52.19242,
      lng: -106.70837
    },
    {
      lat: 52.1961,
      lng: -106.6799
    },
    {
      lat: 52.20631,
      lng: -106.68042
    },
    {
      lat: 52.2026,
      lng: -106.61345
    },
    {
      lat: 52.19755,
      lng: -106.61654
    },
    {
      lat: 52.19003,
      lng: -106.61233
    },
    {
      lat: 52.18356,
      lng: -106.60195
    }
  ];

  finishMap(saskatoonWestCoords, 'saskatoon_west');
}

function output(event, location) {
  stores.filter(store => store.type === location)
      .forEach(store => {
        location = store.areaName;
      });

  let contentString = '<b>' + location + '</b><br>';
  infoWindow.setContent(contentString);
  infoWindow.setPosition(event.latLng);
  infoWindow.open(map);
}

function clickFocus(event, zoom) {
  if (map.getZoom() < zoom) {
    map.setZoom(zoom);
    map.setCenter(event.latLng);
  }
}

function SwitchMapController(controlDiv) {
  controlDiv.style.clear = 'both';

  let goWinnipegUI = document.createElement('div');
  goWinnipegUI.id = 'goWinnipegUI';
  goWinnipegUI.title = 'Click to go to the Winnipeg driverMap';
  controlDiv.appendChild(goWinnipegUI);

  let goCenterText = document.createElement('div');
  goCenterText.id = 'goWinnipegText';
  goCenterText.innerHTML = 'Winnipeg Map';
  goWinnipegUI.appendChild(goCenterText);

  let goSaskatoonUI = document.createElement('div');
  goSaskatoonUI.id = 'goSaskatoonUI';
  goSaskatoonUI.title = 'Click to go to the Saskatoon driverMap';
  controlDiv.appendChild(goSaskatoonUI);

  let setCenterText = document.createElement('div');
  setCenterText.id = 'goSaskatoonText';
  setCenterText.innerHTML = 'Saskatoon Map';
  goSaskatoonUI.appendChild(setCenterText);

  goWinnipegUI.addEventListener('click', function () {
    map.setCenter(winnipeg);
    map.setZoom(11);
  });

  goSaskatoonUI.addEventListener('click', function () {
    map.setCenter(saskatoon);
    map.setZoom(11);
  });
}