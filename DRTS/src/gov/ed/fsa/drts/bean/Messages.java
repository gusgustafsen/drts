package gov.ed.fsa.drts.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

/** Handles application messages */

public enum Messages {
	// Error messages
	/** Invalid format for username */
	ERROR_USERNAME_INVALID_FORMAT,
	/** Username is missing */
	ERROR_USERNAME_MISSING,
	/** Invalid format for password */
	ERROR_PASSWORD_INVALID_FORMAT,
	/** Password is missing */
	ERROR_PASSWORD_MISSING,
	/** Challenge question missing */
	ERROR_CQA_QUESTION_MISSING,
	/** Challenge question invalid format */
	ERROR_CQA_QUESTION_INVALID_FORMAT,
	/** Challenge answer missing */
	ERROR_CQA_ANSWER_MISSING,
	/** Challenge answer invalid format */
	ERROR_CQA_ANSWER_INVALID_FORMAT,
	/** Secure code missing */
	ERROR_SECURE_CODE_MISSING,
	/** Invalid secure code */
	ERROR_SECURE_CODE_INVALID,
	/** Incorrect username/password combination */
	ERROR_USERNAME_PWD_INVALID,
	/** Username not found */
	ERROR_USERNAME_NOT_FOUND,
	/** Account is quarantined */
	ERROR_ACCOUNT_QUARANTINED,
	/** Account is disabled by FSA */
	ERROR_ACCOUNT_DISABLED_FSA,
	/** Incorrect Challenge questions and answers */
	ERROR_WRONG_ANSWERS_FOR_CQS,
	/** Challenge questions are locked for certain period for username */
	USERNAME_CHALLENGEQA_LOCKED,
	/** Incorrect secure code */
	ERROR_SECURE_CODE_INCORRECT,
	/** Account missing email and challenge qas */
	ERROR_MISSING_EMAIL_CQS,
	/** Challenge questions are locked for certain period for password */
	PASSWORD_CHALLENGEQA_LOCKED,
	/** User already exists */
	USERNAME_ALREADY_EXISTS,
	/** Username is quarantined */
	ERROR_USERNAME_IS_QUARANTINED,
	/** User below age */
	USERNAME_BELOW_AGE,
	/** Error message for new password and confirm password not equal */
	PASSWORD_NEW_CONFIRM_MISMATCH,

	// Confirmation messages
	/** Message for password updated */
	CONFIRM_PASSWORD_UPDATED,
	/** Message for account enabled */
	CONFIRM_ACCOUNT_ENABLED,
	/** Message for account disabled */
	CONFIRM_ACCOUNT_DISABLED,
	/** Message for account disabled in user account management */
	CONFIRM_ACCOUNT_DISABLED_UM,
	/** Message for successful email verification */
	CONFIRM_VERIFIED_EMAIL,
	/** Message to indicate secure code email was resent */
	LABEL_RESEND_SC_CONFIRMATION,
	/** Message for successful retrieval of username */
	CONFIRM_FORGOT_UN_CONFIRM,
	/** Message for successful change password with challenge qas */
	CONFIRM_FORGOT_PW_CONFIRM_CQA,
	/** Message for successful change password with secure code */
	CONFIRM_FORGOT_PW_CONFIRM_SC,
	/** Message for successful account creation with application with linked pin */
	CONFIRM_CREATE_ACC_CONFIRM_LINKED_APPLICATION,
	/** Default message for successful account creation with linked pin */
	CONFIRM_CREATE_ACC_CONFIRM_LINKED_DEFAULT,
	/** Message for successful account creation with application */
	CONFIRM_CREATE_ACC_CONFIRM_APPLICATION,
	/** Default message for successful account creation */
	CONFIRM_CREATE_ACC_CONFIRM_DEFAULT,
	/** Message for successful account creation with additional pins */
	CONFIRM_CREATE_ACC_ADD_PIN,
	/** Message for successful uam cqa update */
	CONFIRM_UPDATED_CQA,
	/** Message for successful uam pin association */
	CONFIRM_ASSOCIATED_PIN,
	/** Message for successful uam profile change */
	CONFIRM_PROFILE_CHANGE,
	/** Message for successful uam profile pii change */
	CONFIRM_PROFILE_PII_CHANGE,

	// Status messages
	/** Pending status */
	STATUS_SSA_PENDING,
	/** Matched status */
	STATUS_SSA_FULL_MATCH,
	/** No match status */
	STATUS_SSA_NO_MATCH,
	/** Reported dead */
	STATUS_SSA_REPORTED_DEAD,
	/** PIN challenge phrase not activated */
	STATUS_SSA_CHALLENGE_PHRASE_NOT_ACTIVATED,
	/** Unknown record */
	STATUS_SSA_RECORD_UNKNOWN,
	/** Expired record */
	STATUS_SSA_RECORD_EXPIRED,
	/** PIN challenge deactive */
	STATUS_SSA_RECORD_CHALLENGE_DEACTIVE,
	/** Record deactive */
	STATUS_SSA_RECORD_DEACTIVE,
	/** Record inactive */
	STATUS_SSA_RECORD_INACTIVE,
	/** Record disabled */
	STATUS_SSA_RECORD_DISABLED,
	/** Account enabled */
	STATUS_ACCOUNT_ENABLED,
	/** Account disabled */
	STATUS_ACCOUNT_DISABLED,
	/** Email verified */
	STATUS_EMAIL_VERIFIED,
	/** Email unverified */
	STATUS_EMAIL_UNVERIFIED,

	// Temp messages. Not updated yet.
	/** Invalid username */
	USERNAME_PWD_INVALID,
	/** Key for general error message */
	TRY_AGAIN,
	/** Key for general error message in admin tool */
	TRY_AGAIN_ADMIN,
	/** Key for error message for locked account */
	ACCOUNT_LOCKED,
	/** Key for error message for account disabled by FSA */
	ACCOUNT_DISABLED_FSA,
	/** Key for error message for account disabled by user */
	ACCOUNT_DISABLED_USER,
	/** Key for error message for invalid password */
	PASSWORD_INVALID,
	/** Error message for password expired */
	PASSWORD_EXPIRED,
	/** Error message for forbidden access */
	TAM_OP_FORBIDDEN,
	/** Error message for resource not found */
	TAM_OP_NOT_FOUND,
	/** Successful login, but unknown destination */
	TAM_OP_LOG_SUCCESS,
	/** Password in grace period */
	PASSWORD_GRACE,
	/** Quarantined account */
	ACCOUNT_QUARANTINED,
	/** Account missing challenge qas */
	ACCOUNT_MISSING_CHALLENGE_CQAS,
	/** Challenge qa locked */
	CHALLENGE_QA_LOCKED,
	/** Account email not verified */
	LOGIN_EMAIL_UNVERIFIED,
	/** Account within lockout period */
	ACCOUNT_LOCKOUT_PERIOD,
	/** SSA account logging in with unverified e-mail */
	LOGIN_EMAIL_AS_USERNAME_ERR,
	/** Account updated during login */
	LOGIN_ACCOUNT_UPDATED,
	/** Account missing both challenge questions and emails */
	ACCOUNT_MISSING_EMAIL_CQA,
	/** Account updated message */
	USER_PROFILE_UPDATED,
	/** Account challenge questions update success message. */
	USER_CHALLENGE_QAS_UPDATED,
	/** Account pin lookup update success message */
	USER_PIN_LOOKUP_UPDATE_SUCCESS,

	// Message Strings for ChangePassword
	/** Error message for missing fields */
	PASSWORD_FIELDS_MISSING,
	/** Error message for current and new password mismatch */
	PASSWORD_CURRENT_MISMATCH,
	/** Error message for new password cannot contain spaces */
	PASSWORD_CANNOT_CONTAIN_SPACES,
	/** Error message for new password invalid length */
	PASSWORD_INVALID_LENGTH,
	/** Error message for new and current password being the same */
	PASSWORD_NEW_CURRENT_MATCH,

	// Message Strings for Email Validations
	/** Account without an email address */
	ERROR_EMAIL_MISSING,
	/** Email already exists */
	ERROR_EMAIL_ALREADY_USED,
	/** Params missing for email validation URL */
	ERROR_EMAIL_VALIDATION_MISSING_PARAMS,
	/** Verification ID not found for user */
	ERROR_EMAIL_VALIDATION_MISSING_VID,
	/** URL action does not match with VID action */
	ERROR_EMAIL_VALIDATION_INVALID_ACTION,
	/** Expired URL */
	ERROR_EMAIL_VALIDATION_EXPIRED_URL,
	/** Unknown email validation error */
	ERROR_EMAIL_VALIDATION,
	/** Email required */
	EMAIL_REQUIRED,
	/** Email mismatch */
	ERROR_EMAIL_MUST_MATCH,

	// Message Strings for SSN Validations
	/** Ssn already exists */
	ERROR_SSN_ALREADY_USED,

	/** Ssn on death master list **/
	ERROR_SSN_ON_DEATH_MASTER_LIST,
	/** Ssn is quarantined */
	ERROR_SSN_IS_QUARANTINED,
	/** Ssn not matched to SSA */
	ERROR_SSN_SSA_NOT_MATCHED,
	/** Ssn account already exists with current ssn with SSA matched. */
	ERROR_SSN_SSA_ALREADY_EXISTS,
	/** Message when user types wrong pin for more than 3 times */
	ERROR_PIN_NO_LONGER_LINKABLE,
	/** User when pin is locked with the combination */
	ERROR_SSN_PIN_LOCKED,
	/** User types partial ssn */
	FULL_SSN_REQUIRED,
	/** when either SSN and Lastname and dob are required */
	SSN_DOB_LASTNAME_REQUIRED,

	// Message Strings for general errors
	/** User not found */
	ERROR_USER_NOT_FOUND,
	/** Firstname and or last name are required */
	FIRST_OR_LASTNAME_REQUIRED,
	/** User details does not match with any account */
	DETAILS_DOES_NOT_MATCH,
	/** Users when wrongs answers are entered */
	WRONG_ANSWERS_FOR_CQS,
	/** Message when user doesn,t agrees terms and conditions. */
	USER_TERMS_CONDITIONS_AGREEMENT,
	/** Message for access exception */
	ACCESS_EXCEPTION,

	// Messages related to pin data
	/** Wrong pin entered **/
	ERROR_PIN_DOES_NOT_MATCH,
	/** Wrong answer for challenge question */
	ERROR_PIN_WRONG_ANSWER,
	/** Maximum wrong answer attempts reached */
	ERROR_PIN_WRONG_ANSWER_ATTEMPTS_REACHED,
	/** Message when last name and date of birth and pin not entered. */
	ERROR_PIN_DOB_LASTNAME_REQUIRED,
	/** Message to display when pin record is blocked */
	ERROR_PIN_RECORD_BLOCKED,
	/** Message for incorrect last name and dob */
	ERROR_PIN_RECORD_DETAILS_WRONG,
	/** Message for correct answers for forgot pin */
	PIN_RETRIEVED_FROM_CQA,
	/** Key for message when pins are updated successfully */
	USER_PINS_LINKED_SUCCESS,
	/** Username is empty */
	ERROR_USERNAME_EMPTY,
	/** Username is too long */
	ERROR_USERNAME_TOO_LONG,
	/** Username is too short */
	ERROR_USERNAME_TOO_SHORT,
	/** Password is empty */
	ERROR_PASSWORD_EMPTY,
	/** Password contains spaces */
	ERROR_PASSWORD_CONTAIN_SPACES,
	/** Password is too long */
	ERROR_PASSWORD_TOO_LONG,
	/** Password is too short */
	ERROR_PASSWORD_TOO_SHORT,
	/** Password contains user's name */
	ERROR_PASSWORD_CONTAINS_NAME,
	/** Password missing upper character */
	ERROR_PASSWORD_MISSING_UPPER,
	/** Password missing lower character */
	ERROR_PASSWORD_MISSING_LOWER,
	/** Password missing special character */
	ERROR_PASSWORD_MISSING_SPECIAL,
	/** Password missing numeric */
	ERROR_PASSWORD_MISSING_NUMERIC,
	/** Password contains illegal characters */
	ERROR_PASSWORD_ILLEGAL_CHARACTERS,
	/** Password contains user's dob */
	ERROR_PASSWORD_CONTAINS_DOB,
	/** Password contains user's ssn */
	ERROR_PASSWORD_CONTAINS_SSN,
	/** Password was previously used */
	ERROR_PASSWORD_PREVIOUSLY_USED,
	/** Invalid email format */
	ERROR_EMAIL_INVALID_FORMAT,
	/** Email is too long */
	ERROR_EMAIL_TOO_LONG,
	/** Email is empty */
	ERROR_EMAIL_EMPTY,
	/** First name is too long */
	ERROR_FIRST_NAME_TOO_LONG,
	/** First name is too short */
	ERROR_FIRST_NAME_TOO_SHORT,
	/** Middle name is too long */
	ERROR_MIDDLE_NAME_TOO_LONG,
	/** Last name is too long */
	ERROR_LAST_NAME_TOO_LONG,
	/** Last name is too short */
	ERROR_LAST_NAME_TOO_SHORT,
	/** Invalid pin ssn */
	ERROR_PIN_INVALID_SSN,
	/** Invalid length for pin last name */
	ERROR_PIN_INVALID_LAST_LETTERS_LENGTH,
	/** Invalid cell phone */
	ERROR_CELL_PHONE_INVALID,
	/** Invalid home phone */
	ERROR_HOME_PHONE_INVALID,
	/** Invalid business phone */
	ERROR_BUSINESS_PHONE_INVALID,
	/** Invalid ssn */
	ERROR_SSN_INVALID,
	/** Ssn empty */
	ERROR_SSN_EMPTY,
	/** Suffix is too long */
	ERROR_SUFFIX_TOO_LONG,
	/** Status is too long */
	ERROR_STATUS_TOO_LONG,
	/** Suffix contains spaces */
	ERROR_SUFFIX_CANNOT_CONTAIN_SPACES,
	/** Missing challenge question */
	ERROR_CHALLENGE_QUESTION_MISSING,
	/** Missing challenge answer */
	ERROR_CHALLENGE_ANSWER_MISSING,
	/** No challenge question selected */
	ERROR_CHALLENGE_QUESTION_SELECT,
	/** Change question already selected */
	ERROR_CHALLENGE_QUESTION_ALREADY_ENTERED,
	/** Challenge questions must be unique */
	ERROR_UNIQUE_CHALLENGE_QUESTION_REQUIRED,
	/** Challenge answers must be unique */
	ERROR_UNIQUE_CHALLENGE_ANSWER_REQUIRED,
	/** Challenge question is too long */
	ERROR_CHALLENGE_QUESTION_TOO_LONG,
	/** Challenge answer is too long */
	ERROR_CHALLENGE_ANSWER_TOO_LONG,
	/** Two challenge question must be custom */
	ERROR_TWO_OWN_CHALLENGE_QUESTIONS_REQUIRED,
	/** Challenge Question Significant Date can not be the same as dob */
	ERROR_CQ_SIG_DATE_DOB_SAME,
	/** Username contains spaces */
	ERROR_USERNAME_CONTAINS_SPACES,
	/** Invalid date */
	ERROR_DATE_INVALID,
	/** Msg for password (grace period) will expire in days */
	MESSAGE_PWD_GRACE_DAYS,
	/** Msg for password (grace period) will expire less than 24 hours */
	MESSAGE_PWD_GRACE_HOURS,
	/** Challenge question drop down initial value */
	CHALLENGE_QUESTION_INIT,
	/** Challenge questions */
	CHALLENGE_QUESTIONS,
	/** Forgot Username Missing Challenge Questions */
	ERROR_FORGOT_UN_ID_MISSING_CQ,

	/** Date challenge question */
	LABEL_FULL_PIN_VER_CQA_CQ5,
	/** Confirmation message for re-enabled account during login */
	LABEL_LOGIN_DISABLED_CONFIRMATION,
	/** Error message - Password contains user's name, dob, or ssn */
	ERROR_PASSWORD_CONTAINS_NAME_DOB_SSN,

	/** Report labels */
	LABEL_REPORT_WEEK_TO, LABEL_REPORT_YEAR_COL_HEADER, LABEL_REPORT_TOTALS, LABEL_REPORT_WEEK_NUMBER_COL_HEADER, LABEL_OF_YEAR, LABEL_REPORT_YTD_TOTALS, LABEL_REPORT_TOTAL_WEEK, LABEL_REPORT_ACTIVITY, LABEL_REPORT_TOTALS_FOR_YEAR, LABEL_SYSTEM, LABEL_SYSTEM_NAME, LABEL_REPORT_DATE, LABEL_REPORTS_STANDARD_CALENDARYEAR, LABEL_REPORTS_STANDARD_WEEKNUMBER, LABEL_REPORTS_STANDARD_MONTH, LABEL_REPORTS_ADHOC_PAS_ACCOUNT_STATUS_ACTIVE, LABEL_REPORTS_ADHOC_PAS_ACCOUNT_STATUS_DISABLEDBYFSA, LABEL_REPORTS_ADHOC_PAS_ACCOUNT_STATUS_DISABLEDBYUSER,

	/** Account State Changes Report */
	LABEL_STANDARD_ACCOUNT_STATE_REPORT_COL1_HEADER, LABEL_STANDARD_ACCOUNT_STATE_REPORT_COL2_HEADER, LABEL_STANDARD_ACCOUNT_STATE_REPORT_COL3_HEADER, LABEL_STANDARD_ACCOUNT_STATE_REPORT_COL4_HEADER, LABEL_STANDARD_ACCOUNT_STATE_REPORT_COL5_HEADER, LABEL_STANDARD_ACCOUNT_STATE_REPORT_COL6_HEADER, LABEL_STANDARD_ACCOUNT_STATE_REPORT_COL7_HEADER, LABEL_STANDARD_ACCOUNT_STATE_REPORT_COL8_HEADER, LABEL_STANDARD_ACCOUNT_STATE_REPORT_COL9_HEADER, LABEL_STANDARD_ACCOUNT_STATE_REPORT_FILE_NAME_CSV, LABEL_STANDARD_ACCOUNT_STATE_REPORT_TITLE,

	/** PAS E-mails SSA Account Report */
	LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL1_HEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL2_HEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL3_HEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL4_HEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL5_HEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL5_COL1_SUBHEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL5_COL2_SUBHEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL5_COL3_SUBHEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL5_COL4_SUBHEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_COL5_COL5_SUBHEADER, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_FILE_NAME_CSV, LABEL_STANDARD_EMAILS_SSA_COUNT_REPORT_TITLE,

	/** PAS Authentication Attempts by Client Ytd Report */
	LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_COL1_HEADER, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_COL2_HEADER, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_COL3_HEADER, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_COL4_HEADER, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_COL5_HEADER, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_COL6_HEADER, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_COL7_HEADER, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_COL8_HEADER, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_COL9_HEADER, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_FILE_NAME_CSV, LABEL_STANDARD_AUTH_ATTEMPTS_REPORT_TITLE,

	/** PAS Records Added/Updated By Source Report */
	LABEL_STANDARD_RECORDS_ADDED_REPORT_FILE_NAME_CSV, LABEL_STANDARD_RECORDS_ADDED_REPORT_COL1_HEADER, LABEL_STANDARD_RECORDS_ADDED_REPORT_COL2_HEADER, LABEL_STANDARD_RECORDS_ADDED_REPORT_COL3_HEADER, LABEL_STANDARD_RECORDS_ADDED_REPORT_COL4_HEADER, LABEL_STANDARD_RECORDS_ADDED_REPORT_COL5_HEADER, LABEL_STANDARD_RECORDS_ADDED_REPORT_COL6_HEADER, LABEL_STANDARD_RECORDS_ADDED_REPORT_COL7_HEADER, LABEL_STANDARD_RECORDS_ADDED_REPORT_TITLE,

	/** Number of PAS Record Change Report */
	LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_FILE_NAME_CSV, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL1_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL2_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL3_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL4_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL5_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL6_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL7_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL8_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL9_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL10_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL11_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL12_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL13_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL14_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL15_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL16_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL17_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_COL18_HEADER, LABEL_STANDARD_NUM_RECORDS_CHANGED_REPORT_TITLE,

	/** PAS Account Self-Care Report */
	LABEL_STANDARD_SELF_CARE_REPORT_FILE_NAME_CSV, LABEL_STANDARD_SELF_CARE_REPORT_COL1_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL2_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL3_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL4_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL5_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL6_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL7_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL8_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL9_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL10_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL11_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL12_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_COL13_HEADER, LABEL_STANDARD_SELF_CARE_REPORT_TITLE,

	/** PAS Administrator Functions Report */
	LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_FILE_NAME_CSV, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL1_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL2_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL3_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL4_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL5_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL6_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL7_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL8_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL9_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL10_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_COL11_HEADER, LABEL_STANDARD_ADMIN_FUNCTIONS_REPORT_TITLE,

	/** Total Number of PAS Records on Database Report */
	LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_FILE_NAME_CSV, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_COL1_HEADER, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_COL2_HEADER, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_COL3_HEADER, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_COL4_HEADER, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_COL5_HEADER, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_COL6_HEADER, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_COL7_HEADER, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_COL8_HEADER, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_COL9_HEADER, LABEL_STANDARD_TOTAL_NUM_RECORDS_REPORT_TITLE,

	/** Originating IP Address Report */
	LABEL_STANDARD_ORIGIN_IP_REPORT_FILE_NAME_CSV, LABEL_STANDARD_ORIGIN_IP_REPORT_COL1_HEADER, LABEL_STANDARD_ORIGIN_IP_REPORT_COL2_HEADER, LABEL_STANDARD_ORIGIN_IP_REPORT_COL3_HEADER, LABEL_STANDARD_ORIGIN_IP_REPORT_COL4_HEADER, LABEL_STANDARD_ORIGIN_IP_REPORT_COL5_HEADER, LABEL_STANDARD_ORIGIN_IP_REPORT_COL6_HEADER, LABEL_STANDARD_ORIGIN_IP_REPORT_COL7_HEADER, LABEL_STANDARD_ORIGIN_IP_REPORT_COL8_HEADER, LABEL_STANDARD_ORIGIN_IP_REPORT_TITLE,

	/** PAS E-mail Count Report */
	LABEL_STANDARD_EMAIL_COUNT_REPORT_FILE_NAME_CSV, LABEL_STANDARD_EMAIL_COUNT_REPORT_COL1_HEADER, LABEL_STANDARD_EMAIL_COUNT_REPORT_COL2_HEADER, LABEL_STANDARD_EMAIL_COUNT_REPORT_COL3_HEADER, LABEL_STANDARD_EMAIL_COUNT_REPORT_COL4_HEADER, LABEL_STANDARD_EMAIL_COUNT_REPORT_COL5_HEADER, LABEL_STANDARD_EMAIL_COUNT_REPORT_COL6_HEADER, LABEL_STANDARD_EMAIL_COUNT_REPORT_COL7_HEADER, LABEL_STANDARD_EMAIL_COUNT_REPORT_COL8_HEADER, LABEL_STANDARD_EMAIL_COUNT_REPORT_COL9_HEADER, LABEL_STANDARD_EMAIL_COUNT_REPORT_TITLE,

	/** PAS Registration SSN Match Statistics Report */
	LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_FILE_NAME_CSV, LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_COL2_HEADER, LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_COL3_HEADER, LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_COL4_HEADER, LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_COL5_HEADER, LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_COL6_HEADER, LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_COL7_HEADER, LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_COL8_HEADER, LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_COL9_HEADER, LABEL_STANDARD_REGISTRATION_SSN_MATCH_REPORT_TITLE,

	/** ID verification case report */
	LABEL_ID_VERIFICATION_CASE_REPORT_FILE_NAME_CSV, LABEL_ID_VERIFICATION_CASE_REPORT_TITLE, LABEL_ID_VERIFICATION_CASE_REPORT_COL1_HEADER, LABEL_ID_VERIFICATION_CASE_REPORT_COL2_HEADER, LABEL_ID_VERIFICATION_CASE_REPORT_COL3_HEADER, LABEL_ID_VERIFICATION_CASE_REPORT_COL4_HEADER, LABEL_ID_VERIFICATION_CASE_REPORT_COL5_HEADER, LABEL_ID_VERIFICATION_CASE_REPORT_COL6_HEADER, LABEL_ID_VERIFICATION_CASE_REPORT_COL7_HEADER, LABEL_ID_VERIFICATION_CASE_REPORT_COL8_HEADER,

	/** Forgot Password Missing Challenge Questions */
	ERROR_FORGOT_PW_ID_MISSING_CQ,
	/** Confirmation message for forgot password change through challenge questions */
	LABEL_FORGOT_PW_CONFIRM_BODY_CQAS,
	/** Confirmation message for forgot password change through secure code */
	LABEL_FORGOT_PW_CONFIRM_BODY_SC,
	/** Error message for quarantined email */
	ERROR_EMAIL_IS_QUARANTINED,
	/** Error message for email and confirm email mismatch */
	EMAIL_CONFIRM_MISMATCH,
	/** Error message for UAM empty first and last name */
	ERROR_UAM_FIRST_LAST_REQUIRED,
	/** Error message for UAM empty social security number */
	ERROR_UAM_SSN_REQUIRED,
	/** Error message for UAM empty date of birth */
	ERROR_UAM_DOB_REQUIRED,
	/** Error message for dob less than 13 */
	ERROR_DOB_LESS_THAN_13,
	/** Error message UAM ssn quarantined */
	ERROR_UAM_SSN_QUARANTINE,
	/** Error message UAM ssn is already associated with another account */
	ERROR_UAM_SSN_ALREADY_ASSOCIATED,
	/** Warning message ssn is already associated with another account */
	WARN_UAM_SSN_ALREADY_ASSOCIATED,
	/** Error message UAM ssn, last name, and dob combination already used */
	ERROR_UAM_SSN_LAST_DOB_USED,
	/** Error message for UAM empty email */
	ERROR_UAM_EMAIL_REQUIRED,
	/** Warning message for UAM empty email */
	WARN_UAM_EMAIL_EMPTY,
	/** Error message for UAM email mismatch */
	ERROR_UAM_EMAIL_NOT_MATCH,
	/** Error message UAM email not unique */
	ERROR_UAM_EMAIL_NOT_UNIQUE,
	/** Error message UAM email is quarantined */
	ERROR_UAM_EMAIL_QUARANTINE,
	/** Error UAM username is quarantined */
	ERROR_UAM_USERNAME_QUARANTINE,
	/** ERROR UAM username is not unique */
	ERROR_UAM_USERNAME_NOT_UNIQUE,
	/** Mapping of States and abbreviations */
	STATE_VALUES,
	/** List of foreign country state values */
	FOREIGN_COUNTRY_STATE_VALUES,
	/** Error message for invalid zip code for foreign countries */
	ERROR_UAM_FOREIGN_COUNTRY_ZIP,
	/** Error message for pin association no match found */
	ERROR_UAM_PIN_ASSOC_NO_MATCH,
	/** Error message for pin association locked pin */
	ERROR_UAM_PIN_ASSOC_LOCKED_PIN,
	/** Error message for pin retrieval locked pin challenge question */
	ERROR_PIN_RETRV_RESPONSE_LOCKED,
	/** Error message for pin lookup ssn associated with ssa matched account */
	ERROR_PIN_SSN_ASSOCIATED,
	/** Warning message for pin lookup associated with a non-ssa matched account */
	WARN_PIN_SSN_ASSOCIATED,
	/** Error message for pin lookup ssn, last name, and dob combo already used */
	ERROR_PIN_SSN_LAST_DOB_USED,
	/** Error message for locked pin */
	ERROR_PIN_LOCKED,
	/** Error message for create account first and last name missing */
	ERROR_CREATE_ACC_FIRST_LAST_NAME_REQUIRED,
	/** Error message for create account dob required */
	ERROR_CREATE_ACC_DOB_REQUIRED,
	/** Error message for create account dob is younger than 13 */
	ERROR_CREATE_ACC_YOUNGER_THAN_13,
	/** Error message for create account ssn missing */
	ERROR_CREATE_ACC_SSN_REQUIRED,
	/** Error message for create account ssn quarantined */
	ERROR_CREATE_ACC_SSN_QUARANTINED,
	/** Error message for create account ssn already associated */
	ERROR_CREATE_ACC_SSN_ALREADY_ASSOCIATED,
	/** Warning message for create account ssn already associated */
	WARN_CREATE_ACC_SSN_ALREADY_ASSOCIATED,
	/** Error message for create account ssn, last name, dob combination used */
	ERROR_CREATE_ACC_SSN_LAST_DOB_USED,
	/** Error message for create account email not unique */
	ERROR_CREATE_ACC_EMAIL_NOT_UNIQUE,
	/** Error message for create account email is quarantined */
	ERROR_CREATE_ACC_EMAIL_QUARANTINED,
	/** Error message for create account username not unique */
	ERROR_CREATE_ACC_USERNAME_QUARANTINED,
	/** Error message for create account username is quarantined */
	ERROR_CREATE_ACC_USERNAME_NOT_UNIQUE,
	/** Error message for create account password mismatch */
	ERROR_CREATE_ACC_PASSWORD_MISMATCH,
	/** Error message for create account email mismatch */
	ERROR_CREATE_ACC_EMAIL_MISMATCH,
	/** Warning for create account email missing */
	WARN_CREATE_ACC_EMAIL_EMPTY,
	/** Error message for create account invalid password */
	ERROR_CREATE_ACC_PASSWORD_INVALID,
	/** Error message for create account invalid foreign country/zip combination */
	ERROR_CREATE_ACC_FOREIGN_COUNTRY_ZIP,
	/** Error message for create account challenge questions not unique */
	ERROR_CREATE_CQ_NOT_UNIQUE,
	/** Error message for create account challenge answers not unique */
	ERROR_CREATE_CA_NOT_UNIQUE,
	/** Error Message for create account (full) terms of service not accepted */
	ERROR_CREATE_FULL_TOS_NOT_ACCEPTED,
	/** Error Message for create account (full) terms of service not accepted */
	ERROR_CREATE_BASIC_TOS_NOT_ACCEPTED,
	/** Field err message for required lastname */
	ERR_LASTNAME_REQUIRED,
	/** Field err message for invalid lastname */
	ERR_LASTNAME_INVALID,
	/** Field err message for missing dob */
	ERR_DOB_REQUIRED,
	/** Field err message for invalid dob */
	ERR_DOB_INVALID,
	/** Field err message for missing ssn */
	ERR_SSN_REQUIRED,
	/** Field err message for invalid ssn */
	ERR_SSN_INVALID,
	/** Field err message for ssn starting with 666 */
	ERR_SSN_INVALID_DIGITS,
	/** Field err message for missing email */
	ERR_EMAIL_REQUIRED,
	/** Field err message for invalid email */
	ERR_EMAIL_INVALID,
	/** Field err message for missing secure code */
	ERR_SECURE_CODE_REQUIRED,
	/** Field err message for invalid secure code */
	ERR_SECURE_CODE_INVALID,
	/** Field err message for missing challenge question */
	ERR_CHALLENGE_QUESTION_REQUIRED,
	/** Field err message for invalid challenge question */
	ERR_CHALLENGE_QUESTION_INVALID,
	/** Field err message for missing challenge answer */
	ERR_CHALLENGE_ANSWER_REQUIRED,
	/** Field err message for invalid challenge answer */
	ERR_CHALLENGE_ANSWER_INVALID,
	/** Field err message for missing username */
	ERR_USERNAME_REQUIRED,
	/** Field err message for invalid username */
	ERR_USERNAME_INVALID,
	/** Field err message for missing password */
	ERR_PASSWORD_REQUIRED,
	/** Field err message for invalid password */
	ERR_PASSWORD_INVALID,
	/** Field err message for missing age */
	ERR_AGE_REQUIRED,
	/** Field err message for missing first name */
	ERR_FIRST_NAME_REQUIRED,
	/** Field err message for invalid first name */
	ERR_FIRST_NAME_INVALID,
	/** Field err message for invalid middle initial */
	ERR_MIDDLE_INITIAL_INVALID,
	/** Field err message for invalid partial ssn */
	ERR_PARTIAL_SSN_INVALID,
	/** Field err message for missing pin */
	ERR_PIN_REQUIRED,
	/** Field err message for invalid pin */
	ERR_PIN_INVALID,
	/** Field warning message for missing mailing address */
	WARN_MAILING_ADDR_REQUIRED,
	/** Field err message for invalid mailing address */
	ERR_MAILING_ADDR_INVALID,
	/** Field err message for invalid city */
	ERR_CITY_INVALID,
	/** Field err message for invalid zip code */
	ERR_ZIP_CODE_INVALID,
	/** Field err message for invalid phone */
	ERR_PHONE_INVALID,
	/** Field err message for invalid alt phone */
	ERR_ALT_PHONE_INVALID,
	/** Field err message for missing pin challenge answer */
	ERR_PIN_CHALLENGE_ANSWER_REQUIRED,
	/** Field err message for invalid pin challenge answer */
	ERR_PIN_CHALLENGE_ANSWER_INVALID,
	/** Field err message for invalid date range */
	ERR_DATE_RANGE_INVALID,
	/** Field err message for missing quarantine reason */
	ERR_QUARANTINE_REASON_REQUIRED,
	/** Field err message for invalid quarantine reason */
	ERR_QUARANTINE_REASON_INVALID,
	/** Field err message for missing PAS system it */
	ERR_PAS_SYSTEM_ID_REQUIRED,
	/** Field err message for invalid PAS system id */
	ERR_PAS_SYSTEM_ID_INVALID,
	/** Field err message for missing notes */
	ERR_NOTES_REQUIRED,
	/** Field err message for invalid notes */
	ERR_NOTES_INVALID,
	/** Default return to application message */
	CONFIRM_LOGIN_RETURN_TO_APPLICATION,
	/** Default message when there is no application to return to */
	CONFIRM_LOGIN_RETURN_TO_DEFAULT,
	/** Error message for username for index page for create account */
	ERROR_INDEX_CREATE_USERNAME,
	/** Error message for password mismatch for index page for create account */
	ERROR_INDEX_CREATE_PASSWORD_MISMATCH,
	/** Error message for age for index page for create account */
	ERROR_INDEX_CREATE_AGE,
	/** Error message for email already used for index page for create account */
	ERROR_INDEX_CREATE_EMAIL,
	/** Error message for email quarantine for index page for create account */
	ERROR_INDEX_CREATE_EMAIL_QUARANTINE,
	/** Error message for missing name for pin lookup page */
	ERROR_PIN_NAME,
	/** Error message for quarantined ssn for pin lookup page */
	ERROR_PIN_SSN_QUARANTINE,
	/** Error message for locked pin for pin verification page */
	ERROR_PIN_VER_LOCKED,
	/** Error message for incorrect pin for pin verification page */
	ERROR_PIN_VER_NO_MATCH,
	/** Error message for incorrect pin for forgot pin page */
	ERROR_PIN_RETRV_NO_MATCH,
	/** Error message for missing address field for create account profile page */
	ERROR_CREATE_ACC_ADDRESS,
	/** Message for email resent for create account secure code page */
	LABEL_FULL_PIN_VER_SC_EMAIL_SENT,
	/** Error message for invalid secure code for create account secure code page */
	ERROR_FULL_PIN_VER_SC_SECURE_CODE,
	/** Message for basic account creation when application is available */
	CONFIRM_CREATE_ACC_BASIC_APPLICATION,
	/** Message for basic account creation when application is not available */
	CONFIRM_CREATE_ACC_BASIC_DEFAULT,
	/** Error message for under age 13 for UAM edit profile page */
	ERROR_UAM_EDIT_PROF_AGE,
	/** Error message for UAM forgot pin locked response */
	ERROR_UAM_PIN_RETRV_RESPONSE_LOCKED,
	/** Message after secure code resent for UAM secure code page */
	LABEL_UAM_VERIFICATION_SECURE_CODE_RESEND_SC,
	/** Error message for no pin found for date of birth and last name entered */
	ERROR_UAM_PIN_ASSOC_NO_PIN_FOUND,
	/** Warning message for locked pin from pin lookup page */
	WARN_CREATE_ACC_LOCKED_PIN,
	/** Error message for uam pin lookup for pin mismatch */
	ERROR_UAM_EDIT_PIN_NO_MATCH,
	/** Error message for uam pin lookup locked pin */
	ERROR_UAM_EDIT_PIN_LOCKED_PIN,
	/** Error message for admin quarantine search partial ssn combo */
	ERR_QUARANTINE_LANDING_PARTIAL_SSN_COMBO,
	/** Error message for admin quarantine search dob combo */
	ERR_QUARANTINE_LANDING_DOB_COMBO,
	/** Error message for admin quarantine search last name combo */
	ERR_QUARANTINE_LANDING_LAST_NAME_COMBO,
	/** Error message for admin quarantine search missing criteria */
	ERR_QUARANTINE_LANDING_NO_SEARCH_CRITERIA,
	/** Error message for admin quarantine search no record found */
	ERR_QUARANTINE_LANDING_NO_RECORD_FOUND,
	/** Error message for admin quarantine search invalid criteria */
	ERR_QUARANTINE_LANDING_INVALID_CRITERIA,
	/** Error message for admin quarantine search general error */
	ERR_QUARANTINE_LANDING_SEARCH,
	/** Initial value for admin quarantine code */
	LABEL_QUARANTINE_REASOIN_INIT,
	/** Error mesage for add quarantine invalid reason code */
	ERR_QUARANTINE_ADD_INVALID_REASON_CODE,
	/** Error mesage for add quarantine keylogger code missing required input */
	ERR_QUARANTINE_ADD_KEYLOGGER_MISSING_INPUT,
	/** Error mesage for add quarantine fraud code missing required input */
	ERR_QUARANTINE_ADD_FRAUD_MISSING_INPUT,
	/** Error mesage for add quarantine death code missing required input */
	ERR_QUARANTINE_ADD_DEATH_MISSING_INPUT,
	/** Error mesage for add quarantine ssn already quarantined */
	ERR_QUARANTINE_ADD_SSN_ALREADY_QUARANTINED,
	/** Error mesage for add quarantine email already quarantined */
	ERR_QUARANTINE_ADD_EMAIL_ALREADY_QUARANTINED,
	/** Error mesage for add quarantine username already quarantined */
	ERR_QUARANTINE_ADD_USERNAME_ALREADY_QUARANTINED,
	/** Error message for upload quarantine file unknown format */
	ERR_ADMIN_INDEX_QUARANTINE_FILE_UNKNOWN_FORMAT,
	/** Success message for upload quarantine file */
	CONFIRM_ADMIN_INDEX_QUARANTINE_FILE_UPLOADED,
	/** Success message for adding quarantine record */
	CONFIRM_ADMIN_INDEX_QUARANTINE_DATA_ADDED,
	/** Error message for admin account lookup partial ssn missing required inputs */
	ERR_ADMIN_ACCOUNT_SEARCH_PARTIAL_SSN_COMBO,
	/** Error message for admin account lookup dob missing required inputs */
	ERR_ADMIN_ACCOUNT_SEARCH_DOB_COMBO,
	/** Error message for admin account lookup last name missing required inputs */
	ERR_ADMIN_ACCOUNT_SEARCH_LAST_NAME_COMBO,
	/** Error message for admin account lookup no search criteria enterd */
	ERR_ADMIN_ACCOUNT_SEARCH_NO_CRITERIA,
	/** Error message for admin account lookup no record found */
	ERR_ADMIN_ACCOUNT_SEARCH_NO_RECORD_FOUND,
	/** Error message for admin account update contact info email already used */
	ERR_RECORD_UPDATE_CONTACT_EMAIL_NOT_UNIQUE,
	/** Error message for admin account update contact info email quarantined */
	ERR_RECORD_UPDATE_CONTACT_EMAIL_QUARANTINED,
	/** Error message for admin account foreign country and zip error */
	ERR_RECORD_UPDATE_FOREIGN_COUNTRY_ZIP,
	/** Error message for admin pii update missing first name and last name */
	ERR_RECORD_UPDATE_PII_NAME_MISSING,
	/** Error message for admin pii update missing ssn or dob */
	ERR_RECORD_UPDATE_PII_SSN_DOB_MISSING,
	/** Error message for admin pii update ssn on quarantined list */
	ERR_RECORD_UPDATE_PII_SSN_QUARANTINED,
	/** Error message for admin pii update age less than 13 */
	ERR_RECORD_UPDATE_PII_AGE,
	/** Error message for admin pin association missing name */
	ERR_RECORD_PIN_ASSOC_NAME,
	/** Error message for admin pin association ssn quarantined */
	ERR_RECORD_PIN_ASSOC_SSN_QUARANTINED,
	/** Error message for admin pin association no record found */
	ERR_RECORD_PIN_ASSOC_NO_RECORD_FOUND,
	/** Error message for admin pin association missing input */
	ERR_RECORD_PIN_ASSOC_MISSING_ID_INFO,
	/** Error message for admin pin association pin already associated */
	ERR_RECORD_PIN_ASSOC_PIN_ALREADY_ASSOCIATED,
	/** Label for id verification case attempted close, but email is in use by another PAS user */
	ERR_ID_VERIFICATION_CASE_EMAIL_CONFLICT,
	/** Confirm message for admin account details contact info udpated */
	CONFIRM_ACCOUNT_DETAILS_CONTACT_INFO_UPDATED,
	/** Confirm message for admin account details pii updated */
	CONFIRM_ACCOUNT_DETAILS_PII_UPDATED,
	/** Confirm message for admin account details password reset email sent */
	CONFIRM_ACCOUNT_DETAILS_PWD_RESET,
	/** Confirm message for admin account details email verification sent */
	CONFIRM_ACCOUNT_DETAILS_EMAIL_VERIFICATION_SENT,
	/** Confirm message for admin account details note added */
	CONFIRM_ACCOUNT_DETAILS_NOTE_ADDED,
	/** Confirm message for admin account details fsa id disabled */
	CONFIRM_ACCOUNT_DETAILS_FSA_ID_DISABLED,
	/** Confirm message for admin account details fsa id enabled */
	CONFIRM_ACCOUNT_DETAILS_FSA_ID_ENABLED,
	/** Confirm message for admin account details associated pin */
	CONFIRM_ACCOUNT_DETAILS_PIN_ASSOCIATED,
	/** Confirm message for admin account details record resent to SSA */
	CONFIRM_ACCOUNT_DETAILS_SSA_RESENT,
	/** Confirm message for admin account details record unlock account */
	CONFIRM_ACCOUNT_DETAILS_UNLOCK_ACCOUNT,
	/** Confirm message for admin quarantine landing page record removed */
	CONFIRM_QUARANTINE_LANDING_REMOVE_RECORD,
	/** Label for id verification case created */
	CONFIRM_ID_VERIFICATION_CASE_CREATED,
	/** Label for id verification case updated */
	CONFIRM_ID_VERIFICATION_CASE_UPDATED,
	/** ID verification case created */
	LABEL_ACCOUNT_DETAILS_ACCOUNT_TYPE_FULL,
	/** Label for account details page - limited account */
	LABEL_ACCOUNT_DETAILS_ACCOUNT_TYPE_LIMITED,
	/** Label for account details page - provisional account */
	LABEL_ACCOUNT_DETAILS_ACCOUNT_TYPE_PROVISIONAL,
	/** Label for acount details page - account active */
	LABEL_ACCOUNT_DETAILS_STATUS_ACTIVE,
	/** Label for account details page - account locked */
	LABEL_ACCOUNT_DETAILS_STATUS_LOCKED,
	/** LABEL for account details page - account unlocked */
	LABEL_ACCOUNT_DETAILS_STATUS_UNLOCKED,
	/** Label for account details page - account disabld by FSA */
	LABEL_ACCOUNT_DETAILS_STATUS_DISABLED_FSA,
	/** Label for account details page - account disabled by user */
	LABEL_ACCOUNT_DETAILS_STATUS_DISABLED_USER,
	/** Label for account details page - DMF not listed */
	LABEL_ACCOUNT_DETAILS_DMF_NOT_LISTED,
	/** Label for account details page - DMF listed with proof */
	LABEL_ACCOUNT_DETAILS_DMF_PROOF,
	/** Label for account details page -DMF listed with verification */
	LABEL_ACCOUNT_DETAILS_DMF_VERIFICATION,
	/** Label for account details page - DMF listed without proof or verification */
	LABEL_ACCOUNT_DETAILS_DMF_NO_PROOF_VERIFICATION,
	/** Label for account details page - SSA status not matched on SSN */
	LABEL_ACCOUNT_DETAILS_SSA_NO_MATCH_SSN,
	/** Label for account details page - SSA status not matched on DOB */
	LABEL_ACCOUNT_DETAILS_SSA_NO_MATCH_DOB,
	/** Label for account details page - SSA status not matched on Name */
	LABEL_ACCOUNT_DETAILS_SSA_NO_MATCH_NAME,
	/** Label for account details page - SSA status not matched on SSN unverified */
	LABEL_ACCOUNT_DETAILS_SSA_NO_MATCH_SSN_UNVERIFIED,
	/** Label for account details page - SSA status Deceased */
	LABEL_ACCOUNT_DETAILS_SSA_DEAD,
	/** Label for account details page - SSA status matched */
	LABEL_ACCOUNT_DETAILS_SSA_MATCH,
	/** Label for account details page - SSA status pending */
	LABEL_ACCOUNT_DETAILS_SSA_PENDING,
	/** Label for account details page - English language preference */
	LABEL_ACCOUNT_DETAILS_LANG_ENGLISH,
	/** Label for account details page - Spanish language preference */
	LABEL_ACCOUNT_DETAILS_LANG_SPANISH,
	/** Label for account details page - User not quarantined */
	LABEL_ACCOUNT_DETAILS_NOT_QUARANTINED,
	/** Label for account details page - User quarantined for unknown reason */
	LABEL_ACCOUNT_DETAILS_QUARANTINED_UNKNOWN_REASON,
	/** Message for login locked account page when challenge qas are locked */
	MSG_LOGIN_LOCKED_CQA_LOCKED,
	/** Message for forgot username options page when challenge qas are locked */
	MSG_FORGOT_UN_CQA_LOCKED,
	/** Error message for forgot username challenge qas answered incorrectly five times */
	ERR_FORGOT_UN_CQA_LOCKED,
	/** Error message for forgot password options challenge qas locked */
	ERR_FORGOT_PW_OPT_CQA_LOCKED,
	/** Error message for forgot password challenge qas locked */
	ERR_FORGOT_PW_CQA_LOCKED,
	/** Error message for admin no quarantine record file selected */
	ERR_ADMIN_INDEX_QUARANTINE_FILE_MISSING,
	/** Error message for general admin errors */
	ERR_ADMIN_GENERAL,
	/** Label for current password */
	LABEL_CURRENT_PWD,
	/** Admin Ad hoc user history report no search criteria entered */
	ERR_ADHOC_USER_HISTORY_NO_SEARCH_CRITERIA,
	/** Admin Ad hoc user history report no date range entered */
	ERR_ADHOC_USER_HISTORY_NO_DATE_RANGE,
	/** Admin Ad hoc user history report no search results found */
	ERR_ADHOC_USER_HISTORY_NO_RESULTS,
	/** Admin Ad hoc user history report invalid date range */
	ERR_ADHOC_USER_HISTORY_INVALID_DATE_RANGE,
	/** Admin Ad hoc user history report missing search criteria entered */
	ERR_ADHOC_USER_HISTORY_MISSING_CRITERIA,
	/** Admin Ad hoc user history report has too many criteria entered */
	ERR_ADHOC_USER_HISTORY_TOO_MANY_CRITERIA,
	/** Admin ad hoc user audit no search criteria entered */
	ERR_ADHOC_USER_AUDIT_NO_SEARCH_CRITERIA,
	/** Admin ad hoc user audit no results found */
	ERR_ADHOC_USER_AUDIT_NO_RESULTS,
	/** Admin ad hoc password reset no date range entered */
	ERR_ADHOC_PASSWORD_NO_DATE_RANGE,
	/** Admin ad hoc password reset no results found */
	ERR_ADHOC_PASSWORD_NO_RESULTS,
	/** Admin ad hoc password reset invalid date range */
	ERR_ADHOC_PASSWORD_INVALID_DATE_RANGE,
	/** Err msg when session times out */
	ERR_SESSION_TIME_OUT,
	/** Err msg when TAM session times out */
	ERR_TAM_SESSION_TIME_OUT,
	/** Err msg when no account or verification id can be found for admin reset pwd */
	ERR_FORGOT_PW_NO_RECORD_FOUND,
	/** Err msg when no account or verification id can be found for admin email verification */
	ERR_SECURE_CODE_NO_RECORD_FOUND,
	/** Err msg when there is no email associated with the account */
	ERR_ACCOUNT_MISSING_EMAIL,
	/** Err msg when lastname/dob combo has been used too many times in forgot username */
	ERROR_LAST_NAME_DOB_LOCKOUT,
	/** Confirmation message to admin to force user to change password on next login */
	CONFIRM_FORCE_CHANGE_PASSWORD,
	/** Note for id verification case created */
	NOTE_ID_VERIFICATION_CASE_CREATED,
	/** Note for id verification case closed - accepted */
	NOTE_ID_VERIFICATION_CASE_CLOSED_ACCEPT,
	/** Note for id verification case closed - rejected */
	NOTE_ID_VERIFICATION_CASE_CLOSED_REJECT;

	/** Log4j logger */
	private static final Logger logger = Logger.getLogger(Messages.class);

	/** Properties file name */
	private static final String PROPERTIES_FILE_NAME = "messages";

	/** Resource bundle containing properties */
	private ResourceBundle msgBundle = null;

	/** Language locale */
	private Locale locale = null;

	/** Delimiter for list property values */
	private static final String LIST_DELIMITER = "\\|";

	/** Delimiter for mapping */
	private static final String MAP_DELIMITER = "=";

	public String getStringValue() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Locale newLocale =
				context == null ? locale == null ? Locale.US : locale : context.getViewRoot().getLocale();

		if (msgBundle == null || !locale.equals(newLocale)) {
			locale = newLocale;
			msgBundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME, locale);
		}

		String stringValue;

		try {
			stringValue = msgBundle.getString(name().toLowerCase().replace('_', '.'));
		} catch (final MissingResourceException mre) {
			stringValue = "";
			logger.error("Message properties does not contain the following resource: " + name());
		}

		return stringValue;
	}

	public List<String> getListValue() {
		final String tempString = getStringValue();
		final List<String> listValue = new ArrayList<String>();
		listValue.addAll(Arrays.asList(tempString.split(LIST_DELIMITER)));

		return listValue;
	}

	public LinkedHashMap<String, String> getLinkedMapValue() {
		final LinkedHashMap<String, String> mapValue = new LinkedHashMap<String, String>();
		final List<String> tempList = getListValue();
		final Iterator<String> it = tempList.iterator();
		String tempString;

		while (it.hasNext()) {
			tempString = it.next();
			final String[] mapping = tempString.split(MAP_DELIMITER);
			mapValue.put(mapping[0], mapping[1]);
		}

		return mapValue;
	}
}
