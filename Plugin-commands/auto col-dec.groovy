// Estimate stain vectors automatically
setImageType('BRIGHTFIELD_H_DAB');
getCurrentImageData().getColorDeconvolutionStains().estimateStains(getCurrentImageData());
