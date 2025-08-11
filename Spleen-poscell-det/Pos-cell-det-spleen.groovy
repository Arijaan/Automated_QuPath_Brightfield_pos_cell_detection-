// Create an annotation around the full image
clearAllObjects()
createSelectAllObject(true);

// Other image type settings
setImageType('BRIGHTFIELD_OTHER')
setColorDeconvolutionStains(
    '{"Name" : "spleen-mostupreg", ' +
    '"Stain 1" : "Hematoxylin", ' +
    '"Values 1" : "0.4065948058784219 0.8811972066690934 0.24118902709550336", ' +
    '"Stain 2" : "miR-155", ' +
    '"Values 2" : "0.09634661103100757 0.9892011724629036 0.11044623552143598", ' +
    '"Background" : "223 219 229"}'
);

// Positive cell detection
runPlugin('qupath.imagej.detect.cells.PositiveCellDetection', [
    'detectionImageBrightfield': 'Optical density sum',
    'requestedPixelSizeMicrons': 0.5,
    'backgroundRadiusMicrons': 20.0,
    'backgroundByReconstruction': true,
    'medianRadiusMicrons': 0.0,
    'sigmaMicrons': 1.5,
    'minAreaMicrons': 5.0,
    'maxAreaMicrons': 400.0,
    'threshold': 0.1,
    'maxBackground': 2.0,
    'watershedPostProcess': false,
    'cellExpansionMicrons': 5.0,
    'includeNuclei': true,
    'smoothBoundaries': true,
    'makeMeasurements': true,
    'thresholdCompartment': 'Nucleus: miR-155 OD mean',
    'thresholdPositive1': 0.05,
    'thresholdPositive2': 0.1,
    'thresholdPositive3': 0.15,
    'singleThreshold': false
])
