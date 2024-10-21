package knn1

import knn1.distance.*
import knn1.noise.*

class Settings(val greyScale: Boolean, val noiseLevel: Int, val iterations: Int, distanceType: DistanceType, noiseType: NoiseType) {

    val noiser = when (noiseType) {
        NoiseType.EVEN -> if (greyScale) {
            GreyScaleEvenNoiser()
        } else {
            BWEvenNoiser()
        }
        NoiseType.UNEVEN -> if (greyScale) {
            GreyScaleUnevenNoiser()
        } else {
            BWUnevenNoiser()
        }
    }

    val distance = when (distanceType) {
        DistanceType.REGULAR -> RegularDistanceCalculator()
        DistanceType.CHEBYSHEV -> ChebyshevDistanceCalculator()
        DistanceType.EUCLIDIAN -> EuclidianDistanceCalculator()
        DistanceType.EUCLIDIDAN_SQUARED -> EuclidianSquaredDistanceCalculator()
    }


}