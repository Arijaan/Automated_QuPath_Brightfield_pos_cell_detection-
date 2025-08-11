// Create an annotation around the full image
clearAllObjects()
createSelectAllObject(true);

// Subcellular spot detection for miR-155
runPlugin('qupath.imagej.detect.cells.SubcellularDetection', [
    'detection[miR-155]': 0.12,
    'doSmoothing': true,
    'splitByIntensity': true,
    'splitByShape': false,
    'spotSizeMicrons': 0.5,
    'minSpotSizeMicrons': 0.5,
    'maxSpotSizeMicrons': 2.0,
    'includeClusters': true
])


// Pos Cell detection
runPlugin('qupath.imagej.detect.cells.PositiveCellDetection', [
    'detectionImageBrightfield': 'Optical density sum',
    'requestedPixelSizeMicrons': 0.3,
    'backgroundRadiusMicrons': 30.0,
    'backgroundByReconstruction': true,
    'medianRadiusMicrons': 1.0,
    'sigmaMicrons': 1.5,
    'minAreaMicrons': 5.0,
    'maxAreaMicrons': 500.0,
    'threshold': 0.05,
    'maxBackground': 2.0,
    'watershedPostProcess': true,
    'cellExpansionMicrons': 6.924198250728864,
    'includeNuclei': true,
    'smoothBoundaries': true,
    'makeMeasurements': true,
    'thresholdCompartment': 'Nucleus: miR-155 OD mean',
    'thresholdPositive1': 0.05,
    'thresholdPositive2': 0.1,
    'thresholdPositive3': 0.15,
    'singleThreshold': false
])
