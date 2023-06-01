package com.jualin.apps.utils

import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.data.local.entity.Product
import com.jualin.apps.data.local.entity.Service

object FakeData {
    val products = listOf(
        Product(
            id = 1,
            name = "Kopi Susu",
            price = "16000.00",
            discount = 0.0,
            photoUrl = "https://picsum.photos/300/180",
        ),
        Product(
            id = 2,
            name = "Ice Coffee",
            price = "20000.00",
            discount = 0.0,
            photoUrl = "https://picsum.photos/300/180",
        ),
        Product(
            id = 3,
            name = "Kopi Latte",
            price = "25000.00",
            discount = 0.0,
            photoUrl = "https://picsum.photos/300/180",
        ),
        Product(
            id = 4,
            name = "Kopi Cappucino",
            price = "18000.00",
            discount = 0.0,
            photoUrl = "https://picsum.photos/300/180",
        ),
        Product(
            id = 5,
            name = "Kopi Espresso",
            price = "19000.00",
            discount = 0.0,
            photoUrl = "https://picsum.photos/300/180",
        ),
        Product(
            id = 6,
            name = "Jamu",
            price = "10000.00",
            discount = 0.0,
            photoUrl = "https://picsum.photos/300/180",
        ),
    )

    val services = listOf(
        Service(
            id = 1,
            name = "Service AC",
            price = "100000.00",
            discount = 0.0,
        ),
        Service(
            id = 2,
            name = "Service Kulkas",
            price = "150000.00",
            discount = 0.0,
        ),
        Service(
            id = 3,
            name = "Service Mesin Cuci",
            price = "200000.00",
            discount = 0.0,
        ),
        Service(
            id = 4,
            name = "Service TV",
            price = "100000.00",
            discount = 0.0,
        ),
        Service(
            id = 5,
            name = "Service Komputer",
            price = "100000.00",
            discount = 0.0,
        ),
        Service(
            id = 6,
            name = "Service Laptop",
            price = "100000.00",
            discount = 0.0,
        ),
    )

    val business = listOf(
        Business(
            id = 1,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            products = products,
            services = services,
        ),
        Business(
            id = 2,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            products = products,
        ),
        Business(
            id = 3,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            services = services
        ),
        Business(
            id = 4,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            products = products,
            services = services
        ),
        Business(
            id = 5,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            products = products,
            services = services
        ),
        Business(
            id = 6,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            services = services
        ),
        Business(
            id = 7,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            services = services
        ),
        Business(
            id = 8,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            products = products,
            services = services
        ),
        Business(
            id = 9,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            services = services
        ),
        Business(
            id = 10,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            products = products
        ),
        Business(
            id = 11,
            name = "Kedai Kopi Jualin",
            description = "Kedai Kopi Jualin adalah kedai kopi yang menyediakan berbagai macam kopi dengan harga yang terjangkau",
            imageUrl = "https://picsum.photos/300/180",
            address = "Griya Safiya Blok C.5, Kel. Pakembaran, Kec. Slawi, Kab. Tegal, Jawa Tengah",
            products = products,
            services = services
        ),
    )
}