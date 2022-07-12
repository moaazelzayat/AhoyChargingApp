package com.ahoy.ahoychargingapp.data

class ChargingPointsResponse : ArrayList<ChargingPointsResponseItem>()

data class ChargingPointsResponseItem(
    val AddressInfo: AddressInfo,
    val Connections: List<Connection>,
    val DataProvider: DataProvider,
    val DataProviderID: Int,
    val DataProvidersReference: Any,
    val DataQualityLevel: Int,
    val DateCreated: String,
    val DateLastConfirmed: Any,
    val DateLastStatusUpdate: String,
    val DateLastVerified: String,
    val DatePlanned: Any,
    val GeneralComments: String,
    val ID: Int,
    val IsRecentlyVerified: Boolean,
    val MediaItems: List<MediaItem>,
    val MetadataValues: Any,
    val NumberOfPoints: Int,
    val OperatorID: Int,
    val OperatorInfo: OperatorInfo,
    val OperatorsReference: Any,
    val ParentChargePointID: Any,
    val PercentageSimilarity: Any,
    val StatusType: StatusTypeX,
    val StatusTypeID: Int,
    val SubmissionStatus: SubmissionStatus,
    val SubmissionStatusTypeID: Int,
    val UUID: String,
    val UsageCost: String,
    val UsageType: UsageType,
    val UsageTypeID: Int,
    val UserComments: List<UserComment>
)

data class AddressInfo(
    val AccessComments: String,
    val AddressLine1: String,
    val AddressLine2: String,
    val ContactEmail: Any,
    val ContactTelephone1: String,
    val ContactTelephone2: Any,
    val Country: Country,
    val CountryID: Int,
    val Distance: Any,
    val DistanceUnit: Int,
    val ID: Int,
    val Latitude: Double,
    val Longitude: Double,
    val Postcode: String,
    val RelatedURL: String,
    val StateOrProvince: String,
    val Title: String,
    val Town: String
)

data class CheckinStatusType(
    val ID: Int,
    val IsAutomatedCheckin: Boolean,
    val IsPositive: Boolean,
    val Title: String
)

data class CommentType(
    val ID: Int,
    val Title: String
)

data class Connection(
    val Amps: Int,
    val Comments: String,
    val ConnectionType: ConnectionType,
    val ConnectionTypeID: Int,
    val CurrentType: CurrentType,
    val CurrentTypeID: Int,
    val ID: Int,
    val Level: Level,
    val LevelID: Int,
    val PowerKW: Double,
    val Quantity: Int,
    val Reference: String,
    val StatusType: StatusTypeX,
    val StatusTypeID: Int,
    val Voltage: Int
)

data class ConnectionType(
    val FormalName: String,
    val ID: Int,
    val IsDiscontinued: Boolean,
    val IsObsolete: Boolean,
    val Title: String
)

data class Country(
    val ContinentCode: String,
    val ID: Int,
    val ISOCode: String,
    val Title: String
)

data class CurrentType(
    val Description: String,
    val ID: Int,
    val Title: String
)

data class DataProvider(
    val Comments: Any,
    val DataProviderStatusType: DataProviderStatusType,
    val DateLastImported: Any,
    val ID: Int,
    val IsApprovedImport: Boolean,
    val IsOpenDataLicensed: Boolean,
    val IsRestrictedEdit: Boolean,
    val License: String,
    val Title: String,
    val WebsiteURL: String
)

data class DataProviderStatusType(
    val ID: Int,
    val IsProviderEnabled: Boolean,
    val Title: String
)

data class Level(
    val Comments: String,
    val ID: Int,
    val IsFastChargeCapable: Boolean,
    val Title: String
)

data class MediaItem(
    val ChargePointID: Int,
    val Comment: String,
    val DateCreated: String,
    val ID: Int,
    val IsEnabled: Boolean,
    val IsExternalResource: Boolean,
    val IsFeaturedItem: Boolean,
    val IsVideo: Boolean,
    val ItemThumbnailURL: String,
    val ItemURL: String,
    val MetadataValue: Any,
    val User: User
)

data class OperatorInfo(
    val AddressInfo: Any,
    val BookingURL: Any,
    val Comments: String,
    val ContactEmail: String,
    val FaultReportEmail: String,
    val ID: Int,
    val IsPrivateIndividual: Boolean,
    val IsRestrictedEdit: Boolean,
    val PhonePrimaryContact: String,
    val PhoneSecondaryContact: String,
    val Title: String,
    val WebsiteURL: String
)

data class StatusTypeX(
    val ID: Int,
    val IsOperational: Boolean,
    val IsUserSelectable: Boolean,
    val Title: String
)

data class SubmissionStatus(
    val ID: Int,
    val IsLive: Boolean,
    val Title: String
)

data class UsageType(
    val ID: Int,
    val IsAccessKeyRequired: Boolean,
    val IsMembershipRequired: Boolean,
    val IsPayAtLocation: Boolean,
    val Title: String
)

data class User(
    val APIKey: Any,
    val CurrentSessionToken: Any,
    val DateCreated: Any,
    val DateLastLogin: Any,
    val EmailAddress: Any,
    val EmailHash: Any,
    val ID: Int,
    val Identifier: Any,
    val IdentityProvider: Any,
    val IsCurrentSessionTokenValid: Any,
    val IsEmergencyChargingProvider: Any,
    val IsProfilePublic: Any,
    val IsPublicChargingProvider: Any,
    val Latitude: Any,
    val Location: Any,
    val Longitude: Any,
    val Permissions: Any,
    val PermissionsRequested: Any,
    val Profile: Any,
    val ProfileImageURL: String,
    val ReputationPoints: Int,
    val SyncedSettings: Any,
    val Username: String,
    val WebsiteURL: Any
)

data class UserComment(
    val ChargePointID: Int,
    val CheckinStatusType: CheckinStatusType,
    val CheckinStatusTypeID: Int,
    val Comment: String,
    val CommentType: CommentType,
    val CommentTypeID: Int,
    val DateCreated: String,
    val ID: Int,
    val IsActionedByEditor: Boolean,
    val Rating: Any,
    val RelatedURL: String,
    val User: User,
    val UserName: String
)