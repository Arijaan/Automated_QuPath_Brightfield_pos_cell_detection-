import qupath.lib.images.servers.PixelCalibration
import qupath.lib.images.ImageData
import qupath.lib.display.ChannelDisplayInfo
import javafx.application.Platform

// I absoloutly hate this AI-generated looking ass debug code uugh. But its actually helpfull.

def project = getProject()
if (project == null) {
    print 'No project is open!'
    return
}

def imageEntries = project.getImageList()
if (imageEntries.isEmpty()) {
    print 'No images found in project!'
    return
}

print "Fixing pixel calibration for ${imageEntries.size()} images..."

// First pass: Find a valid pixel calibration
def validPixelCalibration = null
def validImageName = null

for (imageEntry in imageEntries) {
    try {
        def imageData = imageEntry.readImageData()
        if (imageData != null) {
            def server = imageData.getServer()
            def pixelCalibration = server.getPixelCalibration()
            
            def pixelWidthMicrons = pixelCalibration.getPixelWidthMicrons()
            def pixelHeightMicrons = pixelCalibration.getPixelHeightMicrons()
            
            if (!Double.isNaN(pixelWidthMicrons) && !Double.isNaN(pixelHeightMicrons)) {
                validPixelCalibration = pixelCalibration
                validImageName = imageEntry.getImageName()
                break
            }
        }
    } catch (Exception e) {
        // Continue to next image
    }
}

if (validPixelCalibration == null) {
    print "ERROR: No valid pixel calibration found in any image!"
    return
}

print "Using pixel calibration from: ${validImageName}"
print "Pixel size: ${validPixelCalibration.getPixelWidthMicrons()} x ${validPixelCalibration.getPixelHeightMicrons()} ${validPixelCalibration.getPixelWidthUnit()}"

// Second pass: Fix images with NaN pixel calibration
def fixedCount = 0

for (imageEntry in imageEntries) {
    Platform.runLater {
        try {
            // Read the image data
            def imageData = imageEntry.readImageData()
            if (imageData == null) return
            
            // Open the image in the viewer
            getCurrentViewer().setImageData(imageData)
            
            def server = imageData.getServer()
            def currentPixelCalibration = server.getPixelCalibration()
            
            def pixelWidthMicrons = currentPixelCalibration.getPixelWidthMicrons()
            def pixelHeightMicrons = currentPixelCalibration.getPixelHeightMicrons()
            
            if (Double.isNaN(pixelWidthMicrons) || Double.isNaN(pixelHeightMicrons)) {
                print "Fixing: ${imageEntry.getImageName()}"
                
                // Set pixel size using the valid values
                setPixelSizeMicrons(validPixelCalibration.getPixelWidthMicrons(), validPixelCalibration.getPixelHeightMicrons())
                imageEntry.saveImageData(imageData)
                
                fixedCount++
                print "Fixed ${imageEntry.getImageName()}"
            }
            
        } catch (Exception e) {
            print "Error fixing ${imageEntry.getImageName()}: ${e.getMessage()}"
        }
    }
}

print "Pixel calibration fix complete!"


