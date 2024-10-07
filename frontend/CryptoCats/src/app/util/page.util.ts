export interface PaginatedResponse<T> {
  _embedded?: EmbeddedResource<T>;
  _links: PaginationLinks;
  page: PageInfo;
}

// Dynamic embedded resource interface
export interface EmbeddedResource<T> {
  [key : string]: T[]; // The key can be any string, and the value will always be an array of T
}

// Interface for pagination links (HATEOAS links)
export interface PaginationLinks {
  first?: Link;
  self: Link;
  next?: Link;
  last?: Link;
}

// Interface for individual links
export interface Link {
  href: string;
}

// Interface for pagination metadata
export interface PageInfo {
  size: number;
  totalElements: number;
  totalPages: number;
  number: number; // Current page number (0-based index)
}

// Specific example: User entity interface
export interface User {
  id: number;
  capital: number;
  invitedBy: number;
  lastOpenedTime: string;
  tgName: string;
  password?: string | null;
  enabled: boolean;
  accountNonLocked: boolean;
  username: string;
  authorities: Authority[];
  accountNonExpired: boolean;
  credentialsNonExpired: boolean;
}

// Interface for the authorities/roles
export interface Authority {
  authority: string;
}
