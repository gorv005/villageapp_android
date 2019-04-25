package com.villageapp.managers;

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.villageapp.R
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.isEmpty
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class ImageRequestManager private constructor(val imageView: SimpleDraweeView?,
                                              val url: String?,
                                              val uri: Uri?,
                                              val aspectRatio: Float?,
                                              val circular: Boolean,
                                              val roundedTopLeftRadius: Float,
                                              val roundedTopRightRadius: Float,
                                              val roundedBottomLeftRadius: Float,
                                              val roundedBottomRightRadius: Float,
                                              val colorFilter: ColorFilter?,
                                              val placeholderImage: Int?,
                                              val failureImage: Int?,
                                              val resizeOptions: ResizeOptions?,
                                              val scaleType: ScalingUtils.ScaleType?,
                                              val backgroundColor: Int?) {

    class Builder constructor(private val imageView: SimpleDraweeView?) {

        /* optional */
        private var url: String? = null
        private var uri: Uri? = null
        private var aspectRatio: Float? = null
        private var circular: Boolean = false
        private var roundedTopLeftRadius: Float = 0f
        private var roundedTopRightRadius: Float = 0f
        private var roundedBottomLeftRadius: Float = 0f
        private var roundedBottomRightRadius: Float = 0f
        private var colorFilter: ColorFilter? = null
        private var placeholderImage: Int? = R.drawable.placeholder_image_small
        private var failureImage: Int? = null
        private var resizeOptions: ResizeOptions? = null
        private var scaleType: ScalingUtils.ScaleType? = null
        private var backgroundColor: Int? = AndroidUtils.getColor(R.color.transparent)

        /**
         * Build image request manager.
         * this is the required method you need to call to start loading the image after initialising all parameters
         *
         * @return the image request manager
         */
        fun build() {
            ImageRequestManager(imageView,
                    url,
                    uri,
                    aspectRatio,
                    circular,
                    roundedTopLeftRadius,
                    roundedTopRightRadius,
                    roundedBottomLeftRadius,
                    roundedBottomRightRadius,
                    colorFilter,
                    placeholderImage,
                    failureImage,
                    resizeOptions,
                    scaleType,
                    backgroundColor).loadImage()
        }

        /**
         * set the url parameter for the image
         *
         * @param url the url of the image to load
         * @return the builder
         */
        fun url(url: String?) = apply { this.url = url }

        /**
         * Set scale type for the actual image to be loaded
         *
         * @param scaleType
         */
        fun setScaleType(scaleType: ScalingUtils.ScaleType) = apply { this.scaleType = scaleType }

        /**
         * set the URI parameter for the image
         *
         * @param uri the URI of the image to load
         * @return the builder
         */
        fun uri(uri: Uri) = apply { this.uri = uri }

        /**
         * sets the resizing options for the image request
         *
         * @param resizeOptions
         */
        fun setResizeOptions(resizeOptions: ResizeOptions) = apply { this.resizeOptions = resizeOptions }

        /**
         * Set the aspect ratio for the actual loaded image
         * works only when layout_height attribute is set to wrap_content
         *
         * @param aspectRatio the aspect ratio
         * @return the builder
         */
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }


        /**
         * used to load circular image
         *
         * @param circular whether loaded image should be circular
         * @return the builder
         */
        fun circular(circular: Boolean) = apply { this.circular = circular }

        /**
         * rounded corner radius for all 4 corners
         *
         * @param radius the radius
         * @return the builder
         */
        fun roundedCornerRadius(radius: Float) = apply {
            this.roundedTopLeftRadius = radius
            this.roundedTopRightRadius = radius
            this.roundedBottomLeftRadius = radius
            this.roundedBottomRightRadius = radius
        }

        /**
         * set top left corner rounded with specified radius
         *
         * @param radius the radius
         * @return the builder
         */
        fun roundedCornerRadiusTopLeft(radius: Float) = apply { this.roundedTopLeftRadius = radius }

        /**
         * set top right corner rounded with specified radius
         *
         * @param radius the radius
         * @return the builder
         */
        fun roundedCornerRadiusTopRight(radius: Float) = apply { this.roundedTopRightRadius = radius }

        /**
         * set bottom left corner rounded with specified radius
         *
         * @param radius the radius
         * @return the builder
         */
        fun roundedCornerRadiusBottomLeft(radius: Float) = apply { this.roundedBottomLeftRadius = radius }

        /**
         * set bottom right corner rounded with specified radius
         *
         * @param radius the radius
         * @return the builder
         */
        fun roundedCornerRadiusBottomRight(radius: Float) = apply { this.roundedBottomRightRadius = radius }

        /**
         * add color filter to loaded image
         *
         * @param colorFilter the color filter
         * @return the builder
         */
        fun colorFilter(colorFilter: ColorFilter) = apply { this.colorFilter = colorFilter }

        /**
         * add color filter to loaded image using PorterDuffColor
         *
         * @param color the color
         * @param mode  the mode
         * @return the builder
         */
        fun colorFilter(@ColorInt color: Int, mode: PorterDuff.Mode) = apply {
            val filter = PorterDuffColorFilter(color, mode)
            this.colorFilter = filter
        }

        fun setPlaceholderImage(@DrawableRes placeholderImage: Int) = apply { this.placeholderImage = placeholderImage }

        fun setFailureImage(@DrawableRes failureImage: Int) = apply { this.failureImage = failureImage }

        fun setBackgroundColor(@ColorRes backgroundColor: Int) = apply { this.backgroundColor = backgroundColor }
    }

    internal fun loadImage() {
        if (imageView != null) {
            var uriToLoad: Uri? = null
            if (uri != null) {
                uriToLoad = uri
            } else if (!isEmpty(url)) {
                uriToLoad = Uri.parse(url)
            }

            // customizations
            val hierarchy = imageView.hierarchy

            // background color
            if (backgroundColor != null) {
                imageView.setBackgroundColor(backgroundColor)
            } else if (circular) {
                imageView.setBackgroundColor(AndroidUtils.getColor(R.color.transparent))
            } else {
                imageView.setBackgroundColor(AndroidUtils.getColor(R.color.image_placeholder_bg_color))
            }

            // setting placeholder image and scale type for placeholder image
            if (placeholderImage != null) {
                hierarchy.setPlaceholderImage(placeholderImage, ScalingUtils.ScaleType.CENTER_CROP)
            } else if (circular) {
                hierarchy.setPlaceholderImage(R.drawable.placeholder_image_avatar, ScalingUtils.ScaleType.CENTER_INSIDE)
            } else {
                hierarchy.setPlaceholderImage(R.drawable.placeholder_image, ScalingUtils.ScaleType.CENTER_INSIDE)
            }

            // scale type - default is center crop
            //            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);

            if (failureImage != null) {
                hierarchy.setFailureImage(failureImage, ScalingUtils.ScaleType.CENTER_INSIDE)
            }

            if (aspectRatio != null) {
                /* for this to work, provide layout_height for imageview as wrap_content in xml */
                imageView.aspectRatio = aspectRatio
            }

            // color filter
            if (colorFilter != null) {
                hierarchy.setActualImageColorFilter(colorFilter)
            }

            // for rounding corners and for circular images
            var roundingParams = hierarchy.roundingParams
            if (roundingParams == null) {
                roundingParams = RoundingParams()
            }
            roundingParams.setCornersRadii(roundedTopLeftRadius, roundedTopRightRadius, roundedBottomRightRadius, roundedBottomLeftRadius)
            roundingParams.roundAsCircle = circular
            hierarchy.roundingParams = roundingParams

            if (scaleType != null) {
                hierarchy.setActualImageScaleType(scaleType)
            }

            if (resizeOptions == null) {
                imageView.setImageURI(uriToLoad, null)
            } else {
                val request = ImageRequestBuilder.newBuilderWithSource(uriToLoad)
                        .setResizeOptions(resizeOptions)
                        .build()
                imageView.controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(imageView.controller)
                        .setImageRequest(request)
                        .build()
            }
        }
    }

    companion object {

        /**
         * Use to get an instance of the builder object for making image request
         *
         * @param imageView the SimpleDrawee Image view
         * @return the builder
         */
        fun with(imageView: SimpleDraweeView?) = Builder(imageView)
    }
}