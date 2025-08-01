// ImageJ Macro to convert all open images to multi-channel composite color

// Get list of all open image IDs
imageIDs = newArray(nImages);
for (i = 0; i < nImages; i++) {
    selectImage(i + 1);
    imageIDs[i] = getImageID();
}

// Process each image
for (i = 0; i < imageIDs.length; i++) {
    selectImage(imageIDs[i]);
    
    // Convert RGB to composite
    if (bitDepth() == 24) {
        run("RGB Stack");
        run("Make Composite");
        print("Converted RGB image " + getTitle() + " to composite");
    } else {
        getDimensions(width, height, channels, slices, frames);
        if (channels > 1) {
            run("Make Composite");
            print("Converted image " + getTitle() + " to composite");
        } else {
            print("Image " + getTitle() + " has only 1 channel, skipping");
        }
    }
}

print("Conversion complete!");