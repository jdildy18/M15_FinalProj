package com.example.M15_FinalProj;

import com.example.M15_FinalProj.Crypto.CryptoResponse;
import com.example.M15_FinalProj.ISS.SpaceResponse;
import com.example.M15_FinalProj.Weather.WeatherResponse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


import java.text.NumberFormat;
import java.util.*;

@SpringBootApplication
public class M15FinalProjApplication {

	public static void menuDisplay() {
		System.out.println("------WELCOME------");

		System.out.println("1. Weather App- Giving you the weather in a Location Specified.");
		System.out.println("2. International Space Station Finder - Gives geolocation information on where the ISS is.");
		System.out.println("3. International Space Station Finder and Weather App - Gives location on the ISS and the weather in that location.");
		System.out.println("4. Crypto App- Giving you all the information on a specified cryptocurrency.");
		System.out.println("5. Exit");
	}

	public static int answerChoice(int choice) throws InvalidChoiceException {
		if(choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5) {
			return choice;
		} else {
			throw new InvalidChoiceException("You must Input a proper choice.");
		}
	}
	public static void main(String[] args) {
		//SpringApplication.run(M15FinalProjApplication.class, args);

		Scanner scanner = new Scanner(System.in);



		String quit = "n";
		int choice = 0;

		while(quit == "n") {
			menuDisplay();
			try {
				System.out.println("Please select an answer choice provided above: ");
				choice = Integer.parseInt(scanner.nextLine());
				answerChoice(choice);
			} catch(NumberFormatException | InvalidChoiceException nfe) {
				System.out.println("Error - Please enter an answer choice");
				//choice = Integer.parseInt(scanner.nextLine());
			}

			switch (choice) {
				case 1:
					try {
						System.out.println("Please enter a location you would like to check the weather at.");
						String location = scanner.nextLine();

						WebClient clientWeather = WebClient.create("https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=149cc5b1c46090aca9573da30f4bd579");
//						Mono<String> responseWeather = clientWeather
//								.get()
//								.retrieve()
//								.bodyToMono(String.class);
//						String json = responseWeather.share().block();
//						System.out.println(json);


						Mono<WeatherResponse> weatherRetrieve = clientWeather
								.get()
								.retrieve()
								.bodyToMono(WeatherResponse.class);

						WeatherResponse weatherResponse = weatherRetrieve.share().block();


						System.out.println("The location is: " + weatherResponse.name + ", " + weatherResponse.sys.country);
						System.out.println("The current temperature is: " + weatherResponse.main.temp);
						System.out.println("The high today will be " + weatherResponse.main.temp_max + " and the low will be " + weatherResponse.main.temp_min + ".");
						System.out.println("The sunrise is at " +weatherResponse.sys.sunrise+ ", " + "and the sunset is at "+weatherResponse.sys.sunset);
						System.out.println("The weather in this location is: " + weatherResponse.weather[0].description);

						System.out.println(" ");
						System.out.println(" ");
						System.out.println(" ");
					} catch(WebClientResponseException we) {
						int statusCode = we.getRawStatusCode();
						if (statusCode >= 400 && statusCode <500){
							System.out.println("Location does not exist - Client Error." );
						}
						else if (statusCode >= 500 && statusCode <600){
							System.out.println("Server Error");
						}
						System.out.println("Message: " + we.getMessage());
					}
					catch(Exception e) {
						System.out.println("An error occurred.");
					}
					break;


				case 2:

					WebClient clientISS = WebClient.create("http://api.open-notify.org/iss-now.json");
//					Mono<String> responseISS = clientISS
//							.get()
//							.retrieve()
//							.bodyToMono(String.class);
//					String jsonISS = responseISS.share().block();
//					System.out.println(jsonISS);


					Mono<SpaceResponse> issRetrieve = clientISS
							.get()
							.retrieve()
							.bodyToMono(SpaceResponse.class);

					SpaceResponse spaceResponse = issRetrieve.share().block();

					String latitude = spaceResponse.iss_position.latitude;
					String longitude =  spaceResponse.iss_position.longitude;

					WebClient clientWeather2 = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=149cc5b1c46090aca9573da30f4bd579");
//					Mono<String> responseWeather2 = clientWeather2
//							.get()
//							.retrieve()
//							.bodyToMono(String.class);
//					String jsonWeather = responseWeather2.share().block();
//					//System.out.println(jsonWeather);


					Mono<WeatherResponse> weatherRetrieve = clientWeather2
							.get()
							.retrieve()
							.bodyToMono(WeatherResponse.class);

					WeatherResponse weatherResponse2 = weatherRetrieve.share().block();

					System.out.println("Longitude: " + spaceResponse.iss_position.longitude);
					System.out.println("Latitude: " + spaceResponse.iss_position.latitude);


					if (weatherResponse2.sys.country == null) {
						System.out.println("The ISS is not currently above a city or country.");
					} else {
						System.out.println("The International Space Station is currently above: " + weatherResponse2.name + ", " + weatherResponse2.sys.country + ".");
					}
					System.out.println(" ");
					System.out.println(" ");
					System.out.println(" ");
					break;


				case 3:

					WebClient clientISS2 = WebClient.create("http://api.open-notify.org/iss-now.json");
//					Mono<String> responseISS2 = clientISS2
//							.get()
//							.retrieve()
//							.bodyToMono(String.class);
//					//String json4 = responseISS2.share().block();
//					//System.out.println(json4);


					Mono<SpaceResponse> issRetrieve2 = clientISS2
							.get()
							.retrieve()
							.bodyToMono(SpaceResponse.class);

					SpaceResponse spaceResponse2 = issRetrieve2.share().block();
					//need lat and lon variables

					latitude = spaceResponse2.iss_position.latitude;
					longitude = spaceResponse2.iss_position.longitude;

					WebClient clientWeather = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=149cc5b1c46090aca9573da30f4bd579");
//					Mono<String> responseWeather = clientWeather
//							.get()
//							.retrieve()
//							.bodyToMono(String.class);
//					String json5 = responseWeather.share().block();
//					System.out.println(json5);


					Mono<WeatherResponse> weatherRetrieve2 = clientWeather
							.get()
							.retrieve()
							.bodyToMono(WeatherResponse.class);

					WeatherResponse weatherResponse = weatherRetrieve2.share().block();

					System.out.println("Longitude: " + spaceResponse2.iss_position.longitude);
					System.out.println("Latitude: " + spaceResponse2.iss_position.latitude);


					if (weatherResponse.sys.country != null) {
						System.out.println("The International Space Station is currently above: " + weatherResponse.name + ", " + weatherResponse.sys.country + ".");
						System.out.println("The weather in " + weatherResponse.name + ", " + weatherResponse.sys.country + " is: " + weatherResponse.weather[0].description + ".");
						System.out.println("The current temperature is: " + weatherResponse.main.temp);
						System.out.println("The high today will be " + weatherResponse.main.temp_max + " and the low will be " + weatherResponse.main.temp_min + ".");
					} else {
						System.out.println("The ISS is not currently above a city or country.");
						System.out.println("The current temperature is: " + weatherResponse.main.temp);
						System.out.println("The high will be " + weatherResponse.main.temp_max + " and the low will be " + weatherResponse.main.temp_min + ".");
						System.out.println("The weather in this location is/has " + weatherResponse.weather[0].description + ".");
					}
					System.out.println(" ");
					System.out.println(" ");
					System.out.println(" ");
					break;


				case 4:

					try {
						System.out.println("Please enter the symbol of a cryptocurrency for more information on it: ");
						String cryptoName = scanner.nextLine();
						//cryptoName.toUpperCase();


						WebClient clientCrypto = WebClient.create("https://rest.coinapi.io/v1/assets/" + cryptoName + "?apikey=0BBF11BD-BBB7-419B-9D17-309E911313E6");
//						Mono<String> responseCrypto = clientCrypto
//								.get()
//								.retrieve()
//								.bodyToMono(String.class);
//						String json3 = responseCrypto.share().block();
//						//System.out.println(json3);

						Mono<CryptoResponse[]> cryptoRetrieve = clientCrypto
								.get()
								.retrieve()
								.bodyToMono(CryptoResponse[].class);

						CryptoResponse[] cryptoResponse = cryptoRetrieve.share().block();


						System.out.println("The Crypto name is : " + cryptoResponse[0].name);
						System.out.println("The Crypto asset ID is: " + cryptoResponse[0].asset_id);
						float cryptoPrice = cryptoResponse[0].price_usd;
						Locale usa = new Locale("en","US");
						//Currency dollars = Currency.getInstance(usa);
						NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
						System.out.println("The current crypto price is: " + dollarFormat.format(cryptoPrice));
						System.out.println(" ");

					} catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("The crypto symbol does not exist");
					}
					break;
				case 5:
					System.out.println("Thank you! ");
					System.exit(0);
					break;
				default:
					System.out.println("You did not give a proper answer choice: ");
					break;
			}


		}

	}


}
