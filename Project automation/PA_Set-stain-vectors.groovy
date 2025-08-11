import qupath.lib.images.ImageData
import qupath.lib.display.ChannelDisplayInfo
import qupath.lib.color.ColorDeconvolutionStains
import javafx.application.Platform

def project = getProject()
def imageEntries = project.getImageList()

print "Applying settings to ${imageEntries.size()} images..."

// Define the color deconvolution stains
def stains = ColorDeconvolutionStains.parseColorDeconvolutionStainsArg(
    '{"Name" : "miR-155 standard", ' +
    '"Stain 1" : "Hematoxylin", ' +
    '"Values 1" : "0.5911055217542903 0.7811394458377341 0.2010358881093272", ' +
    '"Stain 2" : "miR-155", ' +
    '"Values 2" : "0.393822622853611 0.8876002261269202 0.23889240320307872", ' +
    '"Background" : "255 255 255"}'
)
for (imageEntry in imageEntries) {
    // Read the image data
    def imageData = imageEntry.readImageData()
    if (imageData == null) {
        print "Could not read image data for: ${imageEntry.getImageName()}"
        continue
    }
    
    // Open the image in the viewer using FX thread
    Platform.runLater {
        getCurrentViewer().setImageData(imageData)
        // Set image type

        imageData.setImageType(ImageData.ImageType.BRIGHTFIELD_OTHER)

            // Set color deconvolution stains
    imageData.setColorDeconvolutionStains(stains)
    
    // Save the changes
    imageEntry.saveImageData(imageData)
    
    print "Applied stain vectors to: ${imageEntry.getImageName()}"
    }
}

print "Finished applying stain vectors to all images."
