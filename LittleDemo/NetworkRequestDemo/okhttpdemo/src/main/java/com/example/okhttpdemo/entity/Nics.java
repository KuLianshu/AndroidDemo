package com.example.okhttpdemo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


//@XStreamAlias(“nics”) ：注解 类，对象，字段
@XStreamAlias("nics")
public class Nics {

    //@XStreamImplicit(itemFieldName = “nic”) ：注解 集合
    @XStreamImplicit(itemFieldName = "nic")
    private List<NicBean> nic;

    public List<NicBean> getNic() {
        return nic;
    }

    public void setNic(List<NicBean> nic) {
        this.nic = nic;
    }

    @XStreamAlias("nic")
    public static class NicBean {
        @XStreamAlias("actions")
        private String actions;
        @XStreamAlias("name")
        private String name;
        @XStreamAlias("vm")
        private String vm;
        @XStreamAlias("interface")
        private String interfaceX;
        @XStreamAlias("linked")
        private String linked;
        @XStreamAlias("plugged")
        private String plugged;
        @XStreamAlias("reported_devices")
        private ReportedDevicesBean reported_devices;
        @XStreamAlias("vnic_profile")
        private String vnic_profile;

        @XStreamAlias("mac")
        private String mac;

        public String getActions() {
            return actions;
        }

        public void setActions(String actions) {
            this.actions = actions;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVm() {
            return vm;
        }

        public void setVm(String vm) {
            this.vm = vm;
        }

        public String getInterfaceX() {
            return interfaceX;
        }

        public void setInterfaceX(String interfaceX) {
            this.interfaceX = interfaceX;
        }

        public String getLinked() {
            return linked;
        }

        public void setLinked(String linked) {
            this.linked = linked;
        }

        public String getPlugged() {
            return plugged;
        }

        public void setPlugged(String plugged) {
            this.plugged = plugged;
        }

        public ReportedDevicesBean getReported_devices() {
            return reported_devices;
        }

        public void setReported_devices(ReportedDevicesBean reported_devices) {
            this.reported_devices = reported_devices;
        }

        public String getVnic_profile() {
            return vnic_profile;
        }

        public void setVnic_profile(String vnic_profile) {
            this.vnic_profile = vnic_profile;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public List<LinkBean> getLink() {
            return link;
        }

        public void setLink(List<LinkBean> link) {
            this.link = link;
        }

        @XStreamAlias("reported_devices")
        public static class ReportedDevicesBean {
            @XStreamAlias("reported_device")
            private ReportedDeviceBean reported_device;

            public ReportedDeviceBean getReported_device() {
                return reported_device;
            }

            public void setReported_device(ReportedDeviceBean reported_device) {
                this.reported_device = reported_device;
            }

            @XStreamAlias("reported_device")
            public static class ReportedDeviceBean {
                //@XStreamAsAttribute 注解 属性
                @XStreamAsAttribute
                private String href;
                @XStreamAsAttribute
                private String id;
                @XStreamAlias("name")
                private String name;
                @XStreamAlias("description")
                private String description;
                @XStreamAlias("type")
                private String type;
                @XStreamAlias("ips")
                private IpsBean ips;
                @XStreamAlias("mac")
                private String mac;

                public String getHref() {
                    return href;
                }

                public void setHref(String href) {
                    this.href = href;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public IpsBean getIps() {
                    return ips;
                }

                public void setIps(IpsBean ips) {
                    this.ips = ips;
                }

                public String getMac() {
                    return mac;
                }

                public void setMac(String mac) {
                    this.mac = mac;
                }

                @XStreamAlias("ips")
                public static class IpsBean {
                    @XStreamImplicit(itemFieldName = "ip")
                    private List<IpBean> ip;

                    public List<IpBean> getIp() {
                        return ip;
                    }

                    public void setIp(List<IpBean> ip) {
                        this.ip = ip;
                    }

                    @XStreamAlias("ip")
                    public static class IpBean {
                        @XStreamAlias("address")
                        private String address;
                        @XStreamAlias("version")
                        private String version;

                        public String getAddress() {
                            return address;
                        }

                        public void setAddress(String address) {
                            this.address = address;
                        }

                        public String getVersion() {
                            return version;
                        }

                        public void setVersion(String version) {
                            this.version = version;
                        }
                    }
                }
            }
        }

        @XStreamImplicit(itemFieldName = "link")
        private List<LinkBean> link;

        @XStreamAlias("link")
        public static class LinkBean {
            @XStreamAsAttribute
            private String href;
            @XStreamAsAttribute
            private String rel;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getRel() {
                return rel;
            }

            public void setRel(String rel) {
                this.rel = rel;
            }

            @Override
            public String toString() {
                return "LinkBean{" +
                        "href='" + href + '\'' +
                        ", rel='" + rel + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "NicBean{" +
                    "actions='" + actions + '\'' +
                    ", name='" + name + '\'' +
                    ", vm='" + vm + '\'' +
                    ", interfaceX='" + interfaceX + '\'' +
                    ", linked='" + linked + '\'' +
                    ", plugged='" + plugged + '\'' +
                    ", reported_devices=" + reported_devices +
                    ", vnic_profile='" + vnic_profile + '\'' +
                    ", mac='" + mac + '\'' +
                    ", link=" + link +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Nics{" +
                "nic=" + nic.toString() +
                '}';
    }
}


