// Create an annotation around the full image
clearAllObjects()
createSelectAllObject(true);

// set basal values for Haematoxylin and miR-155
setImageType('BRIGHTFIELD_H_DAB');
setColorDeconvolutionStains(
    '{"Name" : "H-DAB estimated", ' +
    '"Stain 1" : "Hematoxylin", ' +
    '"Values 1" : "0.8312733692741214 0.49839678786450964 0.24614066584371855", ' +
    '"Stain 2" : "miR-155", ' +
    '"Values 2" : "0.26608875540235877 0.9563189855814096 0.12104037369806545", ' +
    '"Background" : "204 202 209"}'
);

// Pos Cell detection
runPlugin('qupath.imagej.detect.cells.PositiveCellDetection', [
    'detectionImageBrightfield': 'Optical density sum',
    'requestedPixelSizeMicrons': 0.3,
    'backgroundRadiusMicrons': 30.0,
    'backgroundByReconstruction': true,
    'medianRadiusMicrons': 1.0,
    'sigmaMicrons': 1.2,
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
    'thresholdPositive1': 0.07,
    'thresholdPositive2': 0.1,
    'thresholdPositive3': 0.15,
    'singleThreshold': false
])

