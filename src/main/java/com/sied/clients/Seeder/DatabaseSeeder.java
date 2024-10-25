package com.sied.clients.Seeder;

import com.sied.clients.entity.boardOfDirector.BoardOfDirector;
import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.controllingEntity.ControllingEntity;
import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.entity.corporateStructure.CorporateStructure;
import com.sied.clients.entity.guarantee.Guarantee;
import com.sied.clients.entity.individualClient.IndividualClient;
import com.sied.clients.entity.jointObligor.JointObligor;
import com.sied.clients.entity.person.Person;
import com.sied.clients.entity.address.Address;
import com.sied.clients.entity.reference.Reference;
import com.sied.clients.entity.relatedPep.RelatedPep;
import com.sied.clients.entity.shareholder.Shareholder;
import com.sied.clients.repository.address.AddressRepository;
import com.sied.clients.repository.controllingEntity.ControllingEntityRepository;
import com.sied.clients.repository.corporateStructure.CorporateStructureRepository;
import com.sied.clients.repository.guarantee.GuaranteeRepository;
import com.sied.clients.repository.jointObligor.JointObligorRepository;
import com.sied.clients.repository.reference.ReferenceRepository;
import com.sied.clients.repository.relatedPep.RelatedPepRepository;
import com.sied.clients.repository.shareholder.ShareholderRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.sied.clients.repository.client.ClientRepository;
import com.sied.clients.repository.corporateClient.CorporateClientRepository;
import com.sied.clients.repository.individualClient.IndividualClientRepository;
import com.sied.clients.repository.person.PersonRepository;
import com.sied.clients.repository.boardOfDirector.BoardOfDirectorRepository;


import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CorporateClientRepository corporateClientRepository;
    @Autowired
    private IndividualClientRepository individualClientRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BoardOfDirectorRepository boardOfDirectorRepository;
    @Autowired
    private ControllingEntityRepository controllingEntitiesRepository;
    @Autowired
    private CorporateStructureRepository corporateStructureRepository;
    @Autowired
    private GuaranteeRepository guaranteeRepository;
    @Autowired
    private JointObligorRepository jointObligorRepository;
    @Autowired
    private ReferenceRepository referencesRepository;
    @Autowired
    private RelatedPepRepository relatedPepRepository;
    @Autowired
    private ShareholderRepository shareholdersRepository;

    private final Faker faker = new Faker(Locale.of("es", "MX"));
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        int numAddresses = 100;
        int numClients = 50;
        int numCorporateClients = 20;
        int numPersons = 50;
        int numIndividualClients = 30;
        int numBoardOfDirectors = 10;
        int numControllingEntities = 5;
        int numCorporateStructures = 10;
        int numGuarantees = 15;
        int numJointObligors = 20;
        int numReferences = 10;
        int numRelatedPep = 5;
        int numShareholders = 10;

        seedAddresses(numAddresses);
        seedPersons(numPersons);
        seedClients(numClients);
        seedIndividualClients(numIndividualClients);
        seedCorporateClients(numCorporateClients);
        seedBoardOfDirectors(numBoardOfDirectors);
        seedControllingEntities(numControllingEntities);
        seedCorporateStructure(numCorporateStructures);
        seedGuarantees(numGuarantees);
        seedJointObligors(numJointObligors);
        seedReferences(numReferences);
        seedRelatedPep(numRelatedPep);
        seedShareholders(numShareholders);
    }

    private void seedAddresses(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            Address address = Address.builder()
                    .betweenStreet(faker.address().streetName())
                    .city(faker.address().city())
                    .externalNumber(faker.address().buildingNumber())
                    .internalNumber(faker.address().buildingNumber())
                    .latitude(faker.address().latitude())
                    .longitude(faker.address().longitude())
                    .municipality(faker.address().city())
                    .neighborhood(faker.address().streetName())
                    .postalCode(faker.address().zipCode())
                    .residenceCountry(faker.address().country())
                    .residenceState(faker.address().state())
                    .status(true)
                    .street(faker.address().streetAddress())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            addressRepository.save(address);
        }
    }

    private void seedPersons(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            Person person = Person.builder()
                    .name(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .maidenName(faker.name().lastName())
                    .birthDate(faker.date().birthday().toInstant().atZone(randomZoneId()).toLocalDate())
                    .birthState(faker.address().state())
                    .curp(faker.regexify("[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}"))
                    .email(faker.internet().emailAddress())
                    .gender(faker.options().option("Male", "Female"))
                    .nationality(faker.address().country())
                    .phoneOne(faker.phoneNumber().phoneNumber())
                    .phoneTwo(faker.phoneNumber().phoneNumber())
                    .rfc(faker.regexify("[A-Z]{4}[0-9]{6}[A-Z0-9]{3}"))
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            personRepository.save(person);
        }
    }

    private void seedClients(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            Address address = addressRepository.findAll().get(random.nextInt((int) addressRepository.count()));

            Client client = Client.builder()
                    .clientType(faker.company().industry())
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .address(address)  // Relación con Address
                    .build();

            clientRepository.save(client);
        }
    }

    private void seedCorporateClients(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            Client client = clientRepository.findAll().get(random.nextInt((int) clientRepository.count()));
            IndividualClient legalRepresentative = individualClientRepository.findAll().get(random.nextInt((int) individualClientRepository.count()));

            CorporateClient corporateClient = CorporateClient.builder()
                    .businessActivity(faker.company().industry())
                    .email(faker.internet().emailAddress())
                    .incorporationDate(faker.date().birthday().toString())
                    .name(faker.company().name())
                    .numberOfEmployees(String.valueOf(faker.number().numberBetween(10, 500)))
                    .phoneOne(faker.phoneNumber().phoneNumber())
                    .phoneTwo(faker.phoneNumber().phoneNumber())
                    .rfc(faker.regexify("[A-Z]{4}[0-9]{6}[A-Z0-9]{3}"))
                    .serialNumber(faker.idNumber().valid())
                    .status(true)
                    .subtype(faker.company().profession())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .client(client)
                    .legalRepresentative(legalRepresentative)
                    .build();

            corporateClientRepository.save(corporateClient);
        }
    }

    private void seedIndividualClients(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            Client client = clientRepository.findAll().get(random.nextInt((int) clientRepository.count()));
            Person person = personRepository.findAll().get(random.nextInt((int) personRepository.count()));

            IndividualClient individualClient = IndividualClient.builder()
                    .maritalStatus(faker.options().option("Single", "Married", "Divorced"))
                    .ocupation(faker.job().title())
                    .status(true)
                    .subtype(faker.company().profession())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .client(client)
                    .person(person)
                    .build();

            individualClientRepository.save(individualClient);
        }
    }

    private void seedBoardOfDirectors(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            CorporateClient corporateClient = corporateClientRepository.findAll().get(random.nextInt((int) corporateClientRepository.count()));

            BoardOfDirector boardOfDirector = BoardOfDirector.builder()
                    .name(faker.name().fullName())
                    .position(faker.job().position())
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .corporateClient(corporateClient)
                    .build();

            boardOfDirectorRepository.save(boardOfDirector);
        }
    }

    private void seedControllingEntities(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            CorporateClient corporateClient = corporateClientRepository.findAll().get(random.nextInt((int) corporateClientRepository.count()));

            ControllingEntity controllingEntity = ControllingEntity.builder()
                    .name(faker.company().name())
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .corporateClient(corporateClient)
                    .build();

            controllingEntitiesRepository.save(controllingEntity);
        }
    }

    private void seedCorporateStructure(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            CorporateClient corporateClient = corporateClientRepository.findAll().get(random.nextInt((int) corporateClientRepository.count()));

            CorporateStructure corporateStructure = CorporateStructure.builder()
                    .name(faker.name().fullName())
                    .position(faker.job().title())
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .corporateClient(corporateClient)
                    .build();

            corporateStructureRepository.save(corporateStructure);
        }
    }

    private void seedGuarantees(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            Client client = clientRepository.findAll().get(random.nextInt((int) clientRepository.count()));

            Guarantee guarantee = Guarantee.builder()
                    .name(faker.company().name())
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .client(client)
                    .build();

            guaranteeRepository.save(guarantee);
        }
    }

    private void seedJointObligors(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            Address address = addressRepository.findAll().get(random.nextInt((int) addressRepository.count()));
            Client client = clientRepository.findAll().get(random.nextInt((int) clientRepository.count()));
            Person person = personRepository.findAll().get(random.nextInt((int) personRepository.count()));

            JointObligor jointObligor = JointObligor.builder()
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .address(address)
                    .client(client)
                    .person(person)
                    .build();

            jointObligorRepository.save(jointObligor);
        }
    }

    private void seedReferences(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            Client client = clientRepository.findAll().get(random.nextInt((int) clientRepository.count()));

            Reference reference = Reference.builder()
                    .contractDate(LocalDateTime.now().minusYears(2))
                    .executive(faker.name().fullName())
                    .institutionName(faker.company().name())
                    .name(faker.name().fullName())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .referenceType(faker.options().option("Bank", "Credit", "Personal"))
                    .relationship(faker.options().option("Friend", "Business Partner"))
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .client(client)
                    .build();

            referencesRepository.save(reference);
        }
    }

    private void seedRelatedPep(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            IndividualClient individualClient = individualClientRepository.findAll().get(random.nextInt((int) individualClientRepository.count()));

            RelatedPep relatedPep = RelatedPep.builder()
                    .position(faker.job().title())
                    .relationship(faker.options().option("Family", "Business Partner"))
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .individualClient(individualClient)
                    .build();

            relatedPepRepository.save(relatedPep);
        }
    }

    private void seedShareholders(int numRecords) {
        for (int i = 0; i < numRecords; i++) {
            CorporateClient corporateClient = corporateClientRepository.findAll().get(random.nextInt((int) corporateClientRepository.count()));

            Shareholder shareholder = Shareholder.builder()
                    .name(faker.name().fullName())
                    .ownershipPercentage(faker.number().randomDouble(2, 5, 50))
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .corporateClient(corporateClient)
                    .build();

            shareholdersRepository.save(shareholder);
        }
    }

    private ZoneId randomZoneId() {
        List<String> availableZoneIds = ZoneId.getAvailableZoneIds().stream().toList();
        return ZoneId.of(availableZoneIds.get(random.nextInt(availableZoneIds.size())));
    }
}