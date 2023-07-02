@file:OptIn(ExperimentalMaterial3Api::class)

package com.ad_coding.recipesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ad_coding.recipesapp.ui.theme.RecipesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesAppTheme {
                Scaffold { padding ->
                    val chooseProduct = remember {
                        ChooseProduct(this)
                    }

                    LaunchedEffect(key1 = true) {
                        chooseProduct.billingSetup()
                        chooseProduct.checkProducts()
                    }

                    val products by chooseProduct.purchases.collectAsStateWithLifecycle()

                    val chooseSubscription = remember {
                        ChooseSubscription(this)
                    }

                    LaunchedEffect(key1 = true) {
                        chooseSubscription.billingSetup()
                        chooseSubscription.hasSubscription()
                    }

                    val currentSubscription by chooseSubscription.subscriptions.collectAsStateWithLifecycle()

                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .padding(vertical = 20.dp)
                    ) {
                        Text(
                            text = "Products",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        ListItem(
                            headlineText = {
                                Text(text = "10 Recipes")
                            },
                            leadingContent = {
                                Text(text = if (products.contains("ten_recipes")) "Purchased" else "Not Purchased")
                            },
                            modifier = Modifier.clickable {
                                chooseProduct.purchase("ten_recipes")
                            }
                        )
                        ListItem(
                            headlineText = {
                                Text(text = "10 Pizza Recipes")
                            },
                            leadingContent = {
                                Text(text = if (products.contains("ten_pizza_recipes")) "Purchased" else "Not Purchased")
                            },
                            modifier = Modifier.clickable {
                                chooseProduct.purchase("ten_pizza_recipes")
                            }
                        )

                        Text(
                            text = "Subscriptions",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        ListItem(
                            headlineText = {
                                Text(
                                    text = "Drink Recipes",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            leadingContent = {
                                Text(text = if (currentSubscription.contains("drinks")) "Purchased" else "Not Purchased")
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            TextButton(
                                onClick = {
                                    chooseSubscription.checkSubscriptionStatus("weekly")
                                }
                            ) {
                                Text(text = "Weekly plan")
                            }
                            TextButton(
                                onClick = {
                                    chooseSubscription.checkSubscriptionStatus("monthly")
                                }
                            ) {
                                Text(text = "Monthly plan")
                            }
                        }
                    }
                }
            }
        }
    }
}