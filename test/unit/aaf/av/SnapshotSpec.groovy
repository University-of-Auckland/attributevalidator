package aaf.av

import grails.test.mixin.*
import grails.plugin.spock.*
import grails.buildtestdata.mixin.Build

import spock.lang.*

@TestFor(aaf.av.Snapshot)
@Build([aaf.av.Snapshot])
class SnapshotSpec extends spock.lang.Specification {

  private Snapshot createValidSnapshot() {
    def snapshot = Snapshot.build(cn: 'test user', mail:'user@test.com', eduPersonAssurance:'urn:mace:aaf.edu.au:iap:id:0', 
                  displayName: 'test user', authenticationMethod:'urn:mace:aaf.edu.au:iap:authn:0', eduPersonScopedAffiliation:'member@test.com',
                  eduPersonTargetedID:'http://idp.test.edu!http://sp.test.com!1234', o: 'Test Org', eduPersonAffiliation: 'member', 
                  eduPersonPrimaryAffiliation: 'member', auEduPersonAffiliation: 'honorary-staff', auEduPersonLegalName: 'test user',
                  eduPersonPrincipalName: 'testuser@test.com',
                  schacHomeOrganization:'test.com', schacHomeOrganizationType:'urn:test', auEduPersonSharedToken:'0DzXjxDQ9eCQu5BMbl4hrodsAYc')

    mockForConstraintsTests(Snapshot, [snapshot])

    assert !snapshot.hasErrors()

    snapshot
  }

  def 'validate cn values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.cn = val
    def state = s.validate()

    then:
    s.cn == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['cn']

    where:
    val << [null, '', 'Mr Test User', 'Test User;Mr Test User', 'Test', 'Test User', 'Sūn Démíng', '孫德明']
    requiredState << [false, false, false, false, true, true, true, true]
    reason << ['nullable', 'blank', 'validator', 'validator', null, null, null, null]
  }

  def 'validate mail values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.mail = val
    def state = s.validate()

    then:
    s.mail == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['mail']

    where:
    val << [null, '', 'testuser', 'user@test.com']
    requiredState << [false, false, false, true]
    reason << ['nullable', 'blank', 'validator', null]
  }

  def 'validate eduPersonPrincipalName values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.eduPersonPrincipalName = val
    def state = s.validate()

    then:
    s.eduPersonPrincipalName == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['eduPersonPrincipalName']

    where:
    val << [null, '', 'testuser', 'user@test.com']
    requiredState << [true, true, false, true]
    reason << ['nullable', 'blank', 'validator', null]
  }

  def 'validate eduPersonOrcid values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.eduPersonOrcid = val
    def state = s.validate()

    then:
    s.eduPersonOrcid == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['eduPersonOrcid']

    where:
    val << [null, '', '0000-0001-1825-000X', 'http://orcid.org/', 'http://orcid.org/0000-0001-1825-000X']
    requiredState << [true, true, false, false, true]
    reason << ['nullable', 'blank', 'validator', 'validator', null]
  }

  def 'validate auEduPersonSharedToken values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.auEduPersonSharedToken = val
    def state = s.validate()

    then:
    s.auEduPersonSharedToken == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['auEduPersonSharedToken']

    where:
    val << [null, '', '0DzXjxDQ9eCQu5BMbl4hrodsA', '0DzXjxDQ9eC u5BMbl4hrodsAYc', '0DzXjxDQ9+CQu5BMbl4hrodsAYc', '0DzXjxDQ9/CQu5BMbl4hrodsAYc', '0DzXjxDQ9eCQu5BMbl4hrodsAYc', '0DzXjxDQ9-CQu5B_bl4hrodsAYc']
    requiredState << [false, false, false, false, false, false, true, true]
    reason << ['nullable', 'blank', 'size', 'matches', 'matches', null, null, null]
  }

  def 'validate displayName values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.displayName = val
    def state = s.validate()

    then:
    s.displayName == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['displayName']

    where:
    val << [null, '', 'testuser', 'Mr Reginald Testbody III jr']
    requiredState << [false, false, true, true]
    reason << ['nullable', 'blank', null, null]
  }

  def 'validate auEduPersonLegalName values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.auEduPersonLegalName = val
    def state = s.validate()

    then:
    s.auEduPersonLegalName == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['auEduPersonLegalName']

    where:
    val << [null, '', 'testuser', 'Test user;Test legal user', 'Mr Reginald Testbody III jr']
    requiredState << [true, true, true, false, true]
    reason << ['nullable', 'blank', null, 'validator', null]
  }

  def 'validate eduPersonAssurance values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.eduPersonAssurance = val
    def state = s.validate()

    then:
    s.eduPersonAssurance == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['eduPersonAssurance']

    where:
    val << [null, '', 'really:secure:ident', 'really:secure:ident;urn:mace:aaf.edu.au:iap:id:2', 'urn:mace:aaf.edu.au:iap:id:2', 'urn:mace:aaf.edu.au:iap:authn:2', 'urn:mace:aaf.edu.au:iap:authn:1;urn:mace:aaf.edu.au:iap:id:3']
    requiredState << [false, false, false, false, true, true, true]
    reason << ['nullable', 'blank', 'validator', 'validator', null, null, null]
  }

  def 'validate authenticationMethod values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.authenticationMethod = val
    def state = s.validate()

    then:
    s.authenticationMethod == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['authenticationMethod']

    where:
    val << [null, '', 'urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport']
    requiredState << [false, false, true]
    reason << ['nullable', 'blank', null]
  }

  def 'validate eduPersonAffiliation values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.eduPersonAffiliation = val
    def state = s.validate()

    then:
    s.eduPersonAffiliation == val

    requiredState == state

    if(!requiredState)
      reason == s.errors['eduPersonAffiliation']

    where:
    val << [null, '', 'notaffiliated', 'staff', 'member;library-walk-in']
    requiredState << [false, false, false, true, true]
    reason << ['nullable', 'blank', 'validator', null, null]
  }

  def 'validate eduPersonPrimaryAffiliation values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.eduPersonPrimaryAffiliation = val
    def state = s.validate()

    then:
    s.eduPersonPrimaryAffiliation == val

    requiredState == state

    if(!requiredState)
      reason == s.errors['eduPersonPrimaryAffiliation']

    where:
    val << [null, '', 'notaffiliated', 'staff', 'member;library-walk-in']
    requiredState << [true, true, false, true, false]
    reason << ['nullable', 'blank', 'validator', null, 'validator']
  }

  def 'validate eduPersonScopedAffiliation values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.eduPersonScopedAffiliation = val
    def state = s.validate()

    then:
    s.eduPersonScopedAffiliation == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['eduPersonScopedAffiliation']

    where:
    val << [null, '', 'library-walk-in', 'notaffiliated@uni.edu', 'member@uni.edu', 'member@uni.edu;staff@uni.edu']
    requiredState << [false, false, false, false, true, true]
    reason << ['nullable', 'blank', 'validator', 'validator', null, null]
  }

  def 'validate auEduPersonAffiliation values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.auEduPersonAffiliation = val
    def state = s.validate()

    then:
    s.auEduPersonAffiliation == val

    requiredState == state

    if(!requiredState)
      reason == s.errors['auEduPersonAffiliation']

    where:
    val << [null, '', 'notaffiliated', 'visiting-staff', 'visiting-staff;postgraduate-research-student']
    requiredState << [true, true, false, true, true]
    reason << ['nullable', 'blank', 'validator', null, null]
  }

  def 'validate eduPersonTargetedID values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.eduPersonTargetedID = val
    def state = s.validate()

    then:
    s.eduPersonTargetedID == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['eduPersonTargetedID']

    where:
    val << [null, '', 'http://idp.uni.edu!http://sp.uni2.edu!123']
    requiredState << [false, false, true]
    reason << ['nullable', 'blank', null]
  }

  def 'validate o values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.o = val
    def state = s.validate()

    then:
    s.o == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['o']

    where:
    val << [null, '', 'string value']
    requiredState << [false, false, true]
    reason << ['nullable', 'blank', null]
  }

  def 'validate givenName values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.givenName = val
    def state = s.validate()

    then:
    s.givenName == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['givenName']

    where:
    val << [null, '', 'givenName;OtherName', 'givenName']
    requiredState << [true, true, false, true]
    reason << [null, null, 'validator', null]
  }

  def 'validate surname values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.surname = val
    def state = s.validate()

    then:
    s.surname == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['surname']

    where:
    val << [null, '', 'surname;othersurname', 'surname']
    requiredState << [true, true, false, true]
    reason << [null, null, 'validator', null]
  }

  def 'validate mobileNumber values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.mobileNumber = val
    def state = s.validate()

    then:
    s.mobileNumber == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['mobileNumber']

    where:
    val << [null, '', 'string value']
    requiredState << [true, true, true]
    reason << [null, null, null]
  }

  def 'validate telephoneNumber values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.telephoneNumber = val
    def state = s.validate()

    then:
    s.telephoneNumber == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['telephoneNumber']

    where:
    val << [null, '', 'string value']
    requiredState << [true, true, true]
    reason << [null, null, null]
  }

  def 'validate postalAddress values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.postalAddress = val
    def state = s.validate()

    then:
    s.postalAddress == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['postalAddress']

    where:
    val << [null, '', 'string value']
    requiredState << [true, true, true]
    reason << [null, null, null]
  }

  def 'validate organizationalUnit values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.organizationalUnit = val
    def state = s.validate()

    then:
    s.organizationalUnit == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['organizationalUnit']

    where:
    val << [null, '', 'string value']
    requiredState << [true, true, true]
    reason << [null, null, null]
  } 
  
  def 'validate schacHomeOrganization values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.schacHomeOrganization = val
    def state = s.validate()

    then:
    s.schacHomeOrganization == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['schacHomeOrganization']

    where:
    val << [null, '', 'subscriber', 'uni.edu.au', 'subscriber.com']
    requiredState << [true, true, false, true, true]
    reason << [null, null, 'matches', 'matches', null]
  } 

  def 'validate schacHomeOrganizationType values'() {
    setup:
    Snapshot s = createValidSnapshot()

    when:
    s.schacHomeOrganizationType = val
    def state = s.validate()

    then:
    s.schacHomeOrganizationType == val
    requiredState == state

    if(!requiredState)
      reason == s.errors['schacHomeOrganizationType']

    where:
    val << [null, '', 'subscriber', 'uni.edu.au', 'urn:mace:terena.org:schac:homeOrganizationType:au:university']
    requiredState << [true, true, false, false, true]
    reason << [null, null, 'matches', 'matches', null]
  }  
}
